package com.eshop.repository;


import com.eshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // find all product by brand name
    Collection<Product> findAllByBrandName(String brandName);
    // find all product by category
    Collection<Product> findAllByCategory(String category);

    // find product bt name
    Product findByName(String name);

    // check if product exist by name
    boolean existsProductByName(String name);
    //check if product exist by brand name
    boolean existsProductByBrandName(String name);
    // find all product by category and brand name and price greater than given price
    Collection<Product> findAllByCategoryAndBrandNameAndPriceGreaterThan(String category, String brandName, Double price);
    // find all product by category and brand name and price less than given price
    Collection<Product> findAllByCategoryAndBrandNameAndPriceLessThan(String category, String brandName, Double price);

    // find all product by price greater or equal to the given price
    Collection<Product> findAllByPriceGreaterThanEqual(Double price);
    //find all product by price less than or equal to the given price
    Collection<Product> findAllByPriceLessThanEqual(Double price);

    // find all product by category and price less than or equal to the given price
    Collection<Product> findAllByCategoryAndPriceLessThanEqual(String category, Double price);
    // find all product by category and price greater or equal to the given price
    Collection<Product> findAllByCategoryAndPriceGreaterThanEqual(String category, Double price);

    // find all product by brand name and price less than or equal to the given price
    Collection<Product> findAllByBrandNameAndPriceLessThanEqual(String brandName, Double price);
    // find all product by brand name and price greater than or equal to the given price
    Collection<Product> findAllByBrandNameAndPriceGreaterThanEqual(String brandName, Double price);





}
