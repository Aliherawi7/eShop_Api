package com.eshop.repository;


import com.eshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // find all product by brand name
    Collection<Product> findAllByBrandName(String brandName);

    // find all product by category
    Collection<Product> findAllByKeywordsContaining(String category);

    // find product bt name
    Product findByName(String name);

    // find product by name keyword
    List<Product> findAllByNameContaining(String keyword);

    // find product by name keyword
    List<Product> findAllByNameContainingOrKeywordsContainingOrBrandNameContaining(String name, String category, String brand);

    // check if product exist by name
    boolean existsProductByName(String name);

    //check if product exist by brand name
    boolean existsProductByBrandNameContaining(String name);

    // find all product by category and brand name and price greater than given price
    Collection<Product> findAllByKeywordsContainingAndBrandNameAndPriceGreaterThan(String category, String brandName, Double price);

    // find all product by category and brand name and price less than given price
    Collection<Product> findAllByKeywordsContainingAndBrandNameAndPriceLessThan(String category, String brandName, Double price);

    // find all product by price greater or equal to the given price
    Collection<Product> findAllByPriceGreaterThanEqual(Double price);

    //find all product by price less than or equal to the given price
    Collection<Product> findAllByPriceLessThanEqual(Double price);

    // find all product by category and price less than or equal to the given price
    Collection<Product> findAllByKeywordsContainingAndPriceLessThanEqual(String category, Double price);

    // find all product by category and price greater or equal to the given price
    Collection<Product> findAllByKeywordsContainingAndPriceGreaterThanEqual(String category, Double price);

    // find all product by brand name and price less than or equal to the given price
    Collection<Product> findAllByBrandNameContainingAndPriceLessThanEqual(String brandName, Double price);

    // find all product by brand name and price greater than or equal to the given price
    Collection<Product> findAllByBrandNameContainingAndPriceGreaterThanEqual(String brandName, Double price);


}
