package com.eshop.repository;


import com.eshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select sum(p.quantityInDepot) from Product p")
    long totalNumberOfProductInDepot();

    // find all product by brand name
    List<Product> findAllByBrandName(String brandName);

    // find all product by category
    List<Product> findAllByKeywordsContaining(String category);

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
    List<Product> findAllByKeywordsContainingAndBrandNameAndPriceGreaterThan(String category, String brandName, Double price);

    // find all product by category and brand name and price less than given price
    List<Product> findAllByKeywordsContainingAndBrandNameAndPriceLessThan(String category, String brandName, Double price);

    // find all product by price greater or equal to the given price
    List<Product> findAllByPriceGreaterThanEqual(Double price);

    //find all product by price less than or equal to the given price
    List<Product> findAllByPriceLessThanEqual(Double price);

    // find all product by category and price less than or equal to the given price
    List<Product> findAllByKeywordsContainingAndPriceLessThanEqual(String category, Double price);

    // find all product by category and price greater or equal to the given price
    List<Product> findAllByKeywordsContainingAndPriceGreaterThanEqual(String category, Double price);

    // find all product by brand name and price less than or equal to the given price
    List<Product> findAllByBrandNameContainingAndPriceLessThanEqual(String brandName, Double price);

    // find all product by brand name and price greater than or equal to the given price
    List<Product> findAllByBrandNameContainingAndPriceGreaterThanEqual(String brandName, Double price);


}
