package com.eshop.service;

import com.eshop.repository.ProductRepository;
import com.eshop.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    //find product by id

    public ResponseEntity<?> getProductById(int id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return new ResponseEntity<>(product.get(), HttpStatus.OK);

        }else{
            return new ResponseEntity<>("product with id: "+id+ " not found!", HttpStatus.NOT_FOUND);
        }
    }

    //find all product in the database by name
    public Product getProductByName(String name){
        return productRepository.findByName(name);
    }
    // save the product to the database
    public ResponseEntity<String> addProduct(Product product){
        productRepository.save(product);
        return new ResponseEntity<>("Successfully saved!", HttpStatus.CREATED);
    }
    // update the existed product
    public ResponseEntity<String> updateProduct(Product product){
        boolean isExist = productRepository.existsById(product.getId());
        if(isExist){
            productRepository.save(product);
            return new ResponseEntity<String>("Successfully updated! !",HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("not Found such product with id: "+product.getId(), HttpStatus.NOT_FOUND);
        }
    }

    //find all product in the database
    public Collection<Product> getAllProducts(){
        return productRepository.findAll();
    }

    // find all product by brand name
    public Collection<Product> getAllByBrandName(String brandName){
        return productRepository.findAllByBrandName(brandName);
    }
    // find all product by category
    public Collection<Product> getAllByCategory(String category){
        return productRepository.findAllByCategory(category);
    }

    // find product by category and brand name and price greater than or equal to the given price
    public Collection<Product> getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(String category, String brandName, Double price){
        return productRepository.findAllByCategoryAndBrandNameAndPriceGreaterThan(category, brandName, price);
    }
    // find product by category and brand name and price less than or equal to the given price
    public Collection<Product> getAllByCategoryAndBrandNameAndPriceLessThanEqual(String category, String brandName, Double price){
        return productRepository.findAllByCategoryAndBrandNameAndPriceLessThan(category, brandName, price);
    }

    // find all product by category and price greater or equal to the given price
    public Collection<Product> getAllByPriceGreaterThanEqual(Double price){
        return productRepository.findAllByPriceGreaterThanEqual(price);
    }
    //find all product by price less than or equal to the given price
    public Collection<Product> getAllByPriceLessThanEqual(Double price){
        return productRepository.findAllByPriceLessThanEqual(price);
    }

    // find all product by category and price less than or equal to the given price
    public Collection<Product> getAllByCategoryAndPriceLessThanEqual(String category, Double price){
        return productRepository.findAllByCategoryAndPriceLessThanEqual(category,price);
    }
    // find all product by category and price greater than or equal to the given price
    public Collection<Product> getAllByCategoryAndPriceGreaterThanEqual(String category, Double price){
        return productRepository.findAllByCategoryAndPriceGreaterThanEqual(category,price);
    }

    // find all product by brand name and price less than or equal to the given price
     public Collection<Product> getAllByBrandNameAndPriceLessThanEqual(String brandName, Double price){
        return productRepository.findAllByBrandNameAndPriceLessThanEqual(brandName, price);
     }
    // find all product by brand name and price greater than or equal to the given price
     public Collection<Product> getAllByBrandNameAndPriceGreaterThanEqual(String brandName, Double price){
        return productRepository.findAllByBrandNameAndPriceGreaterThanEqual(brandName, price);
     }

     // danger area
    //removed product
    public ResponseEntity<String> deleteProductById(Integer id){
        boolean isExist = productRepository.existsById(id);
        productRepository.deleteById(id);
        if(isExist){
            return new ResponseEntity<>("product with id: "+id +" successfully removed", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("not found any product with id: "+id, HttpStatus.NOT_FOUND);
        }

    }
    // remove all all product in the database
    public ResponseEntity<String> deleteAllProducts(){
        productRepository.deleteAll();
        return new ResponseEntity<>("all data removed successfully!", HttpStatus.ACCEPTED);
    }


}
