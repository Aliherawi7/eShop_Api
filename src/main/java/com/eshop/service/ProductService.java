package com.eshop.service;

import com.eshop.dto.PageContainerDTO;
import com.eshop.dto.ProductDTO;
import com.eshop.dto.ProductRegistrationRequest;
import com.eshop.dto.ProductUpdateRequest;
import com.eshop.exception.ProductNotFoundException;
import com.eshop.model.Product;
import com.eshop.repository.CommentRepository;
import com.eshop.repository.ProductRepository;
import com.eshop.utils.BaseURI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final ProductDTOMapper productDTOMapper;
    private final FileStorageService fileStorageService;
    private final HttpServletRequest httpServletRequest;

    public ProductService(ProductRepository productRepository,
                          CommentRepository commentRepository,
                          ProductDTOMapper productDTOMapper,
                          FileStorageService fileStorageService,
                          HttpServletRequest httpServletRequest) {
        this.productRepository = productRepository;
        this.commentRepository = commentRepository;
        this.productDTOMapper = productDTOMapper;
        this.fileStorageService = fileStorageService;
        this.httpServletRequest = httpServletRequest;
    }

    //find product by id
    public ProductDTO getProductById(Long id) {
        String baseURI = BaseURI.getBaseURI(httpServletRequest);
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            ProductDTO productDTO;
            Product product = optionalProduct.get();
            productDTO = productDTOMapper.apply(product);
            ArrayList<String> images = new ArrayList<>();
            productDTO.getImages().forEach(item -> images.add(baseURI + item));
            productDTO.setImages(images);
            productDTO.setReviews(commentRepository.findAllByProductId(product.getId()).size());
            return productDTO;
        }
        throw new ProductNotFoundException("product not found with provided id: " + id);
    }

    // save the product to the database
    public void addProduct(Product product) {
        new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED);
    }

    // save the product with images to the database
    public ProductDTO addProduct(ProductRegistrationRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setBrandName(request.getBrandName());
        product.setPrice(request.getPrice());
        product.setColor(request.getColor());
        product.setDescription(request.getDescriptions());
        product.setQuantityInDepot(request.getQuantityInDepot());
        product.setSize(request.getSize());
        product.setDiscount(request.getDiscount());
        long productId = productRepository.save(product).getId();
        try {
            fileStorageService.storeProductImage(request.getImageSide1(), product.getId() + "-side-1");
            fileStorageService.storeProductImage(request.getImageSide2(), product.getId() + "-side-2");
            fileStorageService.storeProductImage(request.getImageSide3(), product.getId() + "-side-3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getProductById(productId);
    }

    // update the existed product
    public boolean updateProduct(ProductUpdateRequest request) {
        Optional<Product> product = productRepository.findById(request.getProductId());
        if (product.isPresent()) {
            Product updatedProduct = product.get();
            updatedProduct.setName(request.getName());
            updatedProduct.setBrandName(request.getBrandName());
            updatedProduct.setCategory(request.getCategory());
            updatedProduct.setColor(request.getColor());
            updatedProduct.setQuantityInDepot(request.getQuantityInDepot());
            updatedProduct.setDescription(request.getDescriptions());
            updatedProduct.setDiscount(request.getDiscount());
            updatedProduct.setPrice(request.getPrice());
            try {
                fileStorageService.storeProductImage(request.getImageSide1(), request.getName() + "-side-1");
                fileStorageService.storeProductImage(request.getImageSide2(), request.getName() + "-side-2");
                fileStorageService.storeProductImage(request.getImageSide3(), request.getName() + "-side-3");
            } catch (IOException e) {
                e.printStackTrace();
            }
            productRepository.save(updatedProduct);
            return true;
        }
        return false;
    }

//    search products

    public PageContainerDTO searchProducts(int offset, int pageSize, String all) {
        PageContainerDTO pageContainerDTO = getAllProducts(offset, pageSize);
        String finalAll = all.toLowerCase();
        pageContainerDTO.setRecords(pageContainerDTO.getRecords().stream().filter(item -> item.getName().toLowerCase().contains(finalAll) || item.getCategory().toLowerCase().contains(finalAll))
                .collect(Collectors.toList()));
        return pageContainerDTO;
    }

    //find all product in the database
    public PageContainerDTO getAllProducts(int offset, int pageSize) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(offset, pageSize));
        if (productPage == null || productPage.getSize() == 0) {
            return new PageContainerDTO(0, new ArrayList<>());
        }

        return new PageContainerDTO(
                productRepository.count(),
                mapProductsToProductDTOs(productPage.get().collect(Collectors.toList()))
        );
    }

    // find all product by brand name
    public List<ProductDTO> getAllByBrandName(String brandName) {
        return mapProductsToProductDTOs(productRepository.findAllByBrandName(brandName));
    }

    // find product by name containing the keyword
    public List<ProductDTO> getAllProductByNameContaining(String keyword) {
        return mapProductsToProductDTOs(productRepository.findAllByNameContaining(keyword));
    }

    // find product by name containing the keyword
    public List<ProductDTO> getAllProductByNameContainingOrCategoryContainingOrBrandNameContaining(String keyword) {
        return mapProductsToProductDTOs(productRepository.findAllByNameContainingOrKeywordsContainingOrBrandNameContaining(keyword, keyword, keyword));
    }


    // find all product by category
    public List<ProductDTO> getAllByCategory(String category) {
        return mapProductsToProductDTOs(productRepository
                .findAllByKeywordsContaining(category));
    }

    // find product by category and brand name and price greater than or equal to the given price
    public List<ProductDTO> getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(String category, String brandName, Double price) {
        return mapProductsToProductDTOs(productRepository
                .findAllByKeywordsContainingAndBrandNameAndPriceGreaterThan(category, brandName, price)
        );
    }

    // find product by category and brand name and price less than or equal to the given price
    public List<ProductDTO> getAllByCategoryAndBrandNameAndPriceLessThanEqual(String category, String brandName, Double price) {
        return mapProductsToProductDTOs(productRepository
                .findAllByKeywordsContainingAndBrandNameAndPriceLessThan(category, brandName, price));
    }

    // find all product by category and price greater or equal to the given price
    public List<ProductDTO> getAllByPriceGreaterThanEqual(Double price) {
        return mapProductsToProductDTOs(productRepository.findAllByPriceGreaterThanEqual(price));
    }

    //find all product by price less than or equal to the given price
    public List<ProductDTO> getAllByPriceLessThanEqual(Double price) {
        return mapProductsToProductDTOs(productRepository.findAllByPriceLessThanEqual(price));
    }

    // find all product by category and price less than or equal to the given price
    public List<ProductDTO> getAllByCategoryAndPriceLessThanEqual(String category, Double price) {
        return mapProductsToProductDTOs(productRepository.findAllByKeywordsContainingAndPriceLessThanEqual(category, price));
    }

    // find all product by category and price greater than or equal to the given price
    public List<ProductDTO> getAllByCategoryAndPriceGreaterThanEqual(String category, Double price) {
        return mapProductsToProductDTOs(productRepository.findAllByKeywordsContainingAndPriceGreaterThanEqual(category, price));
    }

    // find all product by brand name and price less than or equal to the given price
    public List<ProductDTO> getAllByBrandNameAndPriceLessThanEqual(String brandName, Double price) {
        return mapProductsToProductDTOs(productRepository.findAllByBrandNameContainingAndPriceLessThanEqual(brandName, price));
    }

    // find all product by brand name and price greater than or equal to the given price
    public List<ProductDTO> getAllByBrandNameAndPriceGreaterThanEqual(String brandName, Double price) {
        return mapProductsToProductDTOs(productRepository.findAllByBrandNameContainingAndPriceGreaterThanEqual(brandName, price));
    }

    // danger area
    //removed product
    public ResponseEntity<String> deleteProductById(Long id) {
        boolean isExist = productRepository.existsById(id);
        productRepository.deleteById(id);
        if (isExist) {
            return new ResponseEntity<>("product with id: " + id + " successfully removed", HttpStatus.OK);
        }
        throw new ProductNotFoundException("not found any product with id: " + id);

    }

    // remove all all product in the database
    public ResponseEntity<String> deleteAllProducts() {
        productRepository.deleteAll();
        return new ResponseEntity<>("all data removed successfully!", HttpStatus.ACCEPTED);
    }

    public ProductDTO getProductDTO(Product product) {
        ProductDTO productDTO = productDTOMapper.apply(product);
        productDTO.setReviews(commentRepository.findAllByProductId(product.getId()).size());
        return productDTO;
    }

    public List<ProductDTO> mapProductsToProductDTOs(List<Product> list) {
        if (list == null || list.size() == 0) return new ArrayList<>();
        String baseURI = BaseURI.getBaseURI(httpServletRequest);
        return list.stream()
                .map(this::getProductDTO)
                .peek(item -> {
                    ArrayList<String> images = new ArrayList<>();
                    item.getImages()
                            .forEach(img -> images.add(baseURI + img));
                    item.setImages(images);
                })
                .collect(Collectors.toList());
    }

    /*
     * find products by keywords
     * */

//    public Collection<String> searchWordSuggestion(){
//
//
//    }


}
