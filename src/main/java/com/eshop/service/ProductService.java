package com.eshop.service;

import com.eshop.dto.ProductDTO;
import com.eshop.model.ProductSidesImages;
import com.eshop.repository.CommentRepository;
import com.eshop.repository.ProductImageRepository;
import com.eshop.repository.ProductRepository;
import com.eshop.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CommentRepository commentRepository;

    public ProductService(ProductRepository productRepository, ProductImageRepository productImageRepository, CommentRepository commentRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.commentRepository = commentRepository;
    }

    //find product by id
    public ProductDTO getProductById(Long id) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        ProductDTO productDTO = new ProductDTO();
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            productDTO.setName(product.getName());
            productDTO.setProductId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setBrandName(product.getBrandName());
            productDTO.setCategory(product.getCategory());
            productDTO.setColors(new String[]{product.getColor()});
            productDTO.setDiscount(product.getDiscount());
            productDTO.setPrice(product.getPrice());
            productDTO.setRate(product.getRate());
            productDTO.setProductionDate(product.getProductionDate());
            productDTO.setSize(product.getSize());
            productDTO.setQuantityInDepot(product.getQuantityInDepot());
            productDTO.setReviews(commentRepository.findAllByProductId(product.getId()).size());
            ArrayList<String> description = new ArrayList<>(Arrays.asList(product.getDescription().split("\n")));
            productDTO.setDetails(description);
            ProductSidesImages productSidesImages =
                    productImageRepository.findByProductId(product.getId());
            productDTO.setImages(new ArrayList<>(Arrays.asList(productSidesImages.getSide1(), productSidesImages.getSide2(), productSidesImages.getSide3())));

        }
        return productDTO;
    }

    //find product in the database by name
    public Product getProductByName(String name) {
        return productRepository.findByName(name);
    }

    // save the product to the database
    public ResponseEntity<?> addProduct(Product product) {
        return new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED);
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
        ProductSidesImages productSidesImages = new ProductSidesImages();
        try {
            productSidesImages.setProductId(product.getId());
            productSidesImages.setSide1(files.get(0).getBytes());
            productSidesImages.setSide2(files.get(1).getBytes());
            productSidesImages.setSide3(files.get(2).getBytes());
            productImageRepository.save(productSidesImages);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long productId = productRepository.save(product).getId();
        productSidesImages.setProductId(productId);
        productImageRepository.save(productSidesImages);
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
        } else {
            return new ResponseEntity<>("not found any product with id: " + id, HttpStatus.NO_CONTENT);
        }

    }

    // remove all all product in the database
    public ResponseEntity<String> deleteAllProducts() {
        productRepository.deleteAll();
        return new ResponseEntity<>("all data removed successfully!", HttpStatus.ACCEPTED);
    }

    public ProductDTO getProductDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setProductId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setBrandName(product.getBrandName());
        productDTO.setCategory(product.getCategory());
        productDTO.setColors(new String[]{product.getColor()});
        productDTO.setDiscount(product.getDiscount());
        productDTO.setPrice(product.getPrice());
        productDTO.setRate(product.getRate());
        productDTO.setProductionDate(product.getProductionDate());
        productDTO.setSize(product.getSize());
        productDTO.setQuantityInDepot(product.getQuantityInDepot());
        productDTO.setReviews(commentRepository.findAllByProductId(product.getId()).size());
        ArrayList<String> description = new ArrayList<>(Arrays.asList(product.getDescription().split("\n")));
        productDTO.setDetails(description);

        ProductSidesImages productSidesImages =
                productImageRepository.findByProductId(product.getId());
        ArrayList<byte[]> image = new ArrayList<>();
        image.add(productSidesImages.getSide1());
        productDTO.setImages(image);
        return productDTO;
    }



}
