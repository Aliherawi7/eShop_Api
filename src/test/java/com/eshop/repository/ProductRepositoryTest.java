package com.eshop.repository;

import com.eshop.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ProductRepositoryTest {

    Product product;
    @Autowired
    private ProductRepository underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
        product = new Product(1L, "L2700", "black", "Dell", "computer",
                999.99, "the latest dell product", "medium", 10L, 4d, 10D);
    }

    @Test
    void FindAllByBrandName() {
        //given
        // the product variable is in class
        underTest.save(product);
        //when
        String brandName = "Dell";
        // then
        assertTrue(underTest.findAllByBrandName(brandName).stream().allMatch(item -> {
            return item.getBrandName().equals(brandName);
        }));
    }

    @Test
    void findAllByCategory() {
        // given
        underTest.save(product);
        //when
        String category = product.getCategory();

        //then
        assertTrue(underTest.findAllByKeywordsContaining(category).stream().allMatch(item -> item.getCategory().equals(category)));
    }

    @Test
    void findByName() {
        //given
        underTest.save(product);
        //when
        String name = product.getName();
        //then
        Assertions.assertEquals(name, underTest.findByName(name).getName());
    }

    @Test
    void existsProductByName() {
        //given
        underTest.save(product);
        //when
        String name = product.getName();
        //then
        assertTrue(underTest.existsProductByName(name));
    }

    @Test
    void doesExistProductByBrandName() {

        underTest.save(product);
        //when
        String brandName = product.getBrandName();
        //then
        assertTrue(underTest.existsProductByBrandNameContaining(brandName));
    }

    @Test
    void findAllByCategoryAndBrandNameAndPriceGreaterThan() {
        underTest.save(product);
        //when
        String category = product.getCategory();
        String brandName = product.getCategory();
        double price = product.getPrice();
        //then
        assertTrue(underTest.findAllByKeywordsContainingAndBrandNameAndPriceGreaterThan(category, brandName, price).
                stream().
                allMatch(
                        item -> item.getCategory().equals(category) &&
                                item.getBrandName().equals(brandName) &&
                                item.getPrice() >= price));
    }


    @Test
    void findAllByCategoryAndBrandNameAndPriceLessThan() {
        //given

        underTest.save(product);
        //when
        String category = product.getCategory();
        String brandName = product.getCategory();
        double price = product.getPrice();
        //then
        assertTrue(underTest.findAllByKeywordsContainingAndBrandNameAndPriceGreaterThan(category, brandName, price).
                stream().
                allMatch(
                        item -> item.getCategory().equals(category) &&
                                item.getBrandName().equals(brandName) &&
                                item.getPrice() <= price));
    }

    @Test
    void findAllByPriceGreaterThanEqual() {
        underTest.save(product);
        //when
        double price = product.getPrice();
        //then
        assertTrue(underTest.findAllByPriceGreaterThanEqual(price).
                stream().
                allMatch(
                        item -> item.getPrice() >= price));
    }

    @Test
    void findAllByPriceLessThanEqual() {
        //given
        underTest.save(product);
        //when
        double price = underTest.findByName(product.getName()).getPrice();
        System.out.println(price);
        //then
        assertTrue(underTest.findAllByPriceLessThanEqual(price).
                stream().
                allMatch(
                        item -> item.getPrice() <= price));
    }

    @Test
    void findAllByCategoryAndPriceLessThanEqual() {
        //given
        underTest.save(product);
        //when
        String category = product.getCategory();
        double price = product.getPrice();
        //then
        assertTrue(underTest.findAllByKeywordsContainingAndPriceLessThanEqual(category, price).
                stream().
                allMatch(
                        item -> item.getCategory().equals(category) &&
                                item.getPrice() <= price));

    }

    @Test
    void findAllByCategoryAndPriceGreaterThanEqual() {
        //given
        underTest.save(product);
        //when
        String category = product.getCategory();
        double price = product.getPrice();
        //then
        assertTrue(underTest.findAllByKeywordsContainingAndPriceGreaterThanEqual(category, price).
                stream().
                allMatch(
                        item -> item.getCategory().equals(category) &&
                                item.getPrice() >= price));
    }

    @Test
    void findAllByBrandNameAndPriceLessThanEqual() {
        //given
        underTest.save(product);
        //when
        String brandName = product.getBrandName();
        double price = product.getPrice();
        //then
        assertTrue(underTest.findAllByBrandNameContainingAndPriceLessThanEqual(brandName, price).
                stream().
                allMatch(
                        item -> item.getPrice() <= price && item.getBrandName().equals(brandName)));
    }

    @Test
    void findAllByBrandNameAndPriceGreaterThanEqual() {
        underTest.save(product);
        //when
        String brandName = product.getBrandName();
        double price = product.getPrice();
        //then
        assertTrue(underTest.findAllByBrandNameContainingAndPriceGreaterThanEqual(brandName, price).
                stream().
                allMatch(item -> item.getPrice() >= price && item.getBrandName().equals(brandName)));
    }
}