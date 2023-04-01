package com.eshop.service;

import com.eshop.dto.ProductDTO;
import com.eshop.exception.ProductNotFoundException;
import com.eshop.model.Product;
import com.eshop.repository.CommentRepository;
import com.eshop.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final ProductDTOMapper productDTOMapper;
    private final FileStorageService fileStorageService;

    public ProductService(ProductRepository productRepository,
                          CommentRepository commentRepository,
                          ProductDTOMapper productDTOMapper,
                          FileStorageService fileStorageService) {
        this.productRepository = productRepository;
        this.commentRepository = commentRepository;
        this.productDTOMapper = productDTOMapper;
        this.fileStorageService = fileStorageService;
    }

    //find product by id
    public ProductDTO getProductById(Long id) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            ProductDTO productDTO ;
            Product product = optionalProduct.get();
            productDTO = productDTOMapper.apply(product);
            productDTO.setReviews(commentRepository.findAllByProductId(product.getId()).size());
            return productDTO;
        }
        throw new ProductNotFoundException("product not found with provided id: "+id);
    }

    //find product in the database by name
    public Product getProductByName(String name) {
        return productRepository.findByName(name);
    }

    // save the product to the database
    public void addProduct(Product product) {
        new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED);
    }
    // save the product with images to the database
    public ProductDTO addProduct(ArrayList<MultipartFile> files, Map<String, String> params) {
        Product product = new Product();
        product.setName(params.get("name"));
        product.setCategory(params.get("category"));
        product.setBrandName(params.get("brandName"));
        product.setPrice(Double.parseDouble(params.get("price")));
        product.setProductionDate(LocalDate.parse(params.get("productionDate")));
        product.setColor(params.get("color"));
        product.setDescription(params.get("description"));
        product.setQuantityInDepot(Long.parseLong(params.get("quantityInDepot")));
        product.setSize(params.get("size"));
        product.setDiscount(Double.parseDouble(params.get("discount")));
        try {
            fileStorageService.storeProductImage(files.get(0), product.getId()+"side-1");
            fileStorageService.storeProductImage(files.get(1), product.getId()+"side-2");
            fileStorageService.storeProductImage(files.get(2), product.getId()+"side-3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        long productId = productRepository.save(product).getId();
        return getProductById(productId);
    }

    // update the existed product
    public ResponseEntity<String> updateProduct(Product product) {
        boolean isExist = productRepository.existsById(product.getId());
        if (isExist) {
            productRepository.save(product);
            return new ResponseEntity<>("Successfully updated! !", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("not Found such product with id: " + product.getId(), HttpStatus.NO_CONTENT);
        }
    }

    //find all product in the database
    public Collection<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(this::getProductDTO).collect(Collectors.toList());
    }

    // find all product by brand name
    public Collection<ProductDTO> getAllByBrandName(String brandName) {
        return productRepository.findAllByBrandName(brandName).stream().map(this::getProductDTO).collect(Collectors.toList());
    }

    // find all product by category
    public Collection<ProductDTO> getAllByCategory(String category) {
        return productRepository
                .findAllByCategory(category)
                .stream().map(this::getProductDTO).collect(Collectors.toList());
    }

    // find product by category and brand name and price greater than or equal to the given price
    public Collection<ProductDTO> getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(String category, String brandName, Double price) {
        return productRepository.findAllByCategoryAndBrandNameAndPriceGreaterThan(category, brandName, price)
                .stream().map(this::getProductDTO).collect(Collectors.toList());
    }

    // find product by category and brand name and price less than or equal to the given price
    public Collection<ProductDTO> getAllByCategoryAndBrandNameAndPriceLessThanEqual(String category, String brandName, Double price) {
        return productRepository.findAllByCategoryAndBrandNameAndPriceLessThan(category, brandName, price)
                .stream().map(this::getProductDTO).collect(Collectors.toList());
    }

    // find all product by category and price greater or equal to the given price
    public Collection<ProductDTO> getAllByPriceGreaterThanEqual(Double price) {
        return productRepository.findAllByPriceGreaterThanEqual(price)
                .stream().map(this::getProductDTO).collect(Collectors.toList());
    }

    //find all product by price less than or equal to the given price
    public Collection<ProductDTO> getAllByPriceLessThanEqual(Double price) {
        return productRepository.findAllByPriceLessThanEqual(price)
                .stream().map(this::getProductDTO).collect(Collectors.toList());
    }

    // find all product by category and price less than or equal to the given price
    public Collection<ProductDTO> getAllByCategoryAndPriceLessThanEqual(String category, Double price) {
        return productRepository.findAllByCategoryAndPriceLessThanEqual(category, price)
                .stream().map(this::getProductDTO).collect(Collectors.toList());
    }

    // find all product by category and price greater than or equal to the given price
    public Collection<ProductDTO> getAllByCategoryAndPriceGreaterThanEqual(String category, Double price) {
        return productRepository.findAllByCategoryAndPriceGreaterThanEqual(category, price)
                .stream().map(this::getProductDTO).collect(Collectors.toList());
    }

    // find all product by brand name and price less than or equal to the given price
    public Collection<ProductDTO> getAllByBrandNameAndPriceLessThanEqual(String brandName, Double price) {
        return productRepository.findAllByBrandNameAndPriceLessThanEqual(brandName, price)
                .stream().map(this::getProductDTO).collect(Collectors.toList());
    }

    // find all product by brand name and price greater than or equal to the given price
    public Collection<ProductDTO> getAllByBrandNameAndPriceGreaterThanEqual(String brandName, Double price) {
        return productRepository.findAllByBrandNameAndPriceGreaterThanEqual(brandName, price)
                .stream().map(this::getProductDTO).collect(Collectors.toList());
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

    public ProductDTO getProductDTO(Product product){
        ProductDTO productDTO = productDTOMapper.apply(product);
        productDTO.setReviews(commentRepository.findAllByProductId(product.getId()).size());
        return productDTO;
    }



}
