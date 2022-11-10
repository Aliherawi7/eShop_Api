package com.eshop.service;

import com.eshop.model.Product;
import com.eshop.repository.CommentRepository;
import com.eshop.repository.ProductImageRepository;
import com.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductImageRepository productImageRepository;
    @Mock
    private CommentRepository commentRepository;
    private ProductService underTest;
    private Product product;


    @BeforeEach
    void setUp() {
        underTest = new ProductService(productRepository, productImageRepository, commentRepository );
        product = new Product(1l, "L2400", "green",
                "dell", "laptop", 599.99,
                "description", LocalDate.now(), "small",120l, 5d, 10D);
    }


    /*
     * Tests of Query methods
     * */
    @Test
    void getProductByName() {
        // given
        underTest.addProduct(product);
        // when
        String name = product.getName();
        // then
        ArgumentCaptor<Product> productArgumentCaptor =
                ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productArgumentCaptor.capture());
        Product capturedProduct =
                productArgumentCaptor.getValue();
        assertEquals(name, capturedProduct.getName());

    }

    @Test
    void addProduct() {
        // when
        underTest.addProduct(product);
        // then
        ArgumentCaptor<Product> productArgumentCaptor =
                ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productArgumentCaptor.capture());

        Product capturedProduct =
                productArgumentCaptor.getValue();

        assertEquals(capturedProduct, product);
    }


    @Test
    void updateProduct() {
        //given
        product.setName("L2400 i7");

        //when
        product.setBrandName("apple");
        underTest.addProduct(product);

        //then
        ArgumentCaptor<Product> productArgumentCaptor =
                ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productArgumentCaptor.capture());

        Product capturedProduct =
                productArgumentCaptor.getValue();
        assertEquals(capturedProduct, product);
    }

    @Test
    void getAllProducts() {
        //when
        underTest.getAllProducts();
        //then
        verify(productRepository).findAll();
    }

    @Test
    void getAllByBrandName() {
        //when
        String brandName = "apple";
        underTest.getAllByBrandName(brandName);
        //then
        verify(productRepository).findAllByBrandName(brandName);
    }

    @Test
    void getAllByCategory() {
        //when
        String category = "laptop";
        underTest.getAllByCategory(category);
        //then
        verify(productRepository).findAllByCategory(category);
    }

    @Test
    void getAllByCategoryAndBrandNameAndPriceGreaterThanEqual() {
        //when
        String branName = "apple";
        String category = "laptop";
        Double price = 99.5;
        underTest.getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(category, branName, price);
        //then
        verify(productRepository).findAllByCategoryAndBrandNameAndPriceGreaterThan(category, branName, price);

    }

    @Test
    void getAllByCategoryAndBrandNameAndPriceLessThanEqual() {
        //when
        String brandName = "apple";
        String category = "laptop";
        Double price = 99.5;
        underTest.getAllByCategoryAndBrandNameAndPriceLessThanEqual(category, brandName, price);
    }

    @Test
    void getAllByPriceGreaterThanEqual() {
        //when
        Double price = 45.5;
        underTest.getAllByPriceGreaterThanEqual(price);
        //then
        verify(productRepository).findAllByPriceGreaterThanEqual(price);
    }

    @Test
    void findAllByPriceLessThanEqual() {
        //when
        Double price = 110.5;
        underTest.getAllByPriceLessThanEqual(price);
        //then
        verify(productRepository).findAllByPriceLessThanEqual(price);
    }

    @Test
    void getAllByCategoryAndPriceLessThanEqual() {
        //when
        Double price = 400.20;
        String category = "laptop";
        underTest.getAllByCategoryAndPriceLessThanEqual(category, price);
        //then
        verify(productRepository).findAllByCategoryAndPriceLessThanEqual(category, price);
    }

    @Test
    void getAllByCategoryAndPriceGreaterThanEqual() {
        //when
        String category = "mobile";
        Double price = 200.99;
        underTest.getAllByCategoryAndPriceGreaterThanEqual(category, price);
        //then
        verify(productRepository).findAllByCategoryAndPriceGreaterThanEqual(category, price);
    }

    @Test
    void getAllByBrandNameAndPriceLessThanEqual() {
        //when
        String brandName = "samsung";
        Double price = 999.99;
        underTest.getAllByBrandNameAndPriceLessThanEqual(brandName, price);
        //then
        verify(productRepository).findAllByBrandNameAndPriceLessThanEqual(brandName, price);
    }

    @Test
    void getAllByBrandNameAndPriceGreaterThanEqual() {
        //when
        String brandName = "apple";
        Double price = 300.50;
        underTest.getAllByBrandNameAndPriceGreaterThanEqual(brandName, price);
        //then
        verify(productRepository).findAllByBrandNameAndPriceGreaterThanEqual(brandName, price);
    }

    @Test
    void deleteProductById() {
        //when
        Long id = 1l;
        underTest.deleteProductById(id);
        //then
        verify(productRepository).deleteById(id);
    }

    @Test
    void deleteAllProducts() {
        //when
        underTest.deleteAllProducts();
        //then
        verify(productRepository).deleteAll();
    }


}