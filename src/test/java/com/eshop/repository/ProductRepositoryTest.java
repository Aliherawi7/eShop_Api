package com.eshop.repository;

import com.eshop.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {
    @Autowired
    private ProductRepository underTest;
    private Product product = new Product(null, "core i9 4.8Ghz","black","imgeUrl","Dell","computer",
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
        assertEquals(true, underTest.findAllByCategory(category).stream().allMatch(item ->{
            return item.getCategory().equals(category);
        }));
    }

    @Test
    void findByName() {
        //given
        //when
        String name = product.getName();

        //then
        assertEquals(name, underTest.findByName(name));
    }

    @Test
    void existsProductByName() {
        //given
        //when
        boolean exist = product.getName()
    }

    @Test
    void existsProductByBrandName() {
    }

    @Test
    void existsProductByNameAndBrandNameAndPrice() {
    }

    @Test
    void findAllByCategoryAndBrandNameAndPriceGreaterThan() {
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