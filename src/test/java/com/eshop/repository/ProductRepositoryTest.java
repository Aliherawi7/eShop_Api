package com.eshop.repository;

import com.eshop.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {
    @Autowired
    private ProductRepository underTest;
    private Product product = new Product(null, "L2700","black","imgeUrl","Dell","computer",
            999.99,"the latest dell product",LocalDate.now(),"meduim");


    @Test
    void findAllByBrandName() {
        //given
            // the product is in class
        //when
        String brandName = "Dell";
        // then
        assertEquals(true, underTest.findAllByBrandName(brandName).stream().allMatch(item ->{
            return item.getBrandName().equals(brandName);
        }));
    }

    @Test
    void findAllByCategory() {
        // given

        //when
        String category = product.getCategory();

        //then
        assertTrue(underTest.findAllByCategory(category).stream().allMatch(item -> item.getCategory().equals(category)));
    }

    @Test
    void findByName() {
        //given
        //when
        String name = product.getName();

        //then
        assertEquals(name, underTest.findByName(name).getName());
    }

    @Test
    void existsProductByName() {
        //given
        //when
        String name = product.getName();
        //then
        assertTrue(underTest.existsProductByName(name));
    }

    @Test
    void existsProductByBrandName() {
        //when
        String brandName = product.getBrandName();
        //then
        assertTrue(underTest.existsProductByBrandName(brandName));
    }

    @Test
    void findAllByCategoryAndBrandNameAndPriceGreaterThan() {

        assertIterableEquals();
    }

    @Test
    void findAllByCategoryAndBrandNameAndPriceLessThan() {
    }

    @Test
    void findAllByPriceGreaterThanEqual() {
    }

    @Test
    void findAllByPriceLessThanEqual() {
    }

    @Test
    void findAllByCategoryAndPriceLessThanEqual() {
    }

    @Test
    void findAllByCategoryAndPriceGreaterThanEqual() {
    }

    @Test
    void findAllByBrandNameAndPriceLessThanEqual() {
    }

    @Test
    void findAllByBrandNameAndPriceGreaterThanEqual() {
    }
}