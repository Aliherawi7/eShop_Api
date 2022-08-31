package com.eshop.service;

import com.eshop.model.Product;
import com.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    private ProductService underTest;
    private Product product;



    @BeforeEach
    void setUp(){
        underTest = new ProductService(productRepository);
        product = new Product(1, "L2400", "green", "img",
                "dell", "laptop", 599.99,
                "description", LocalDate.now(), "small");
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
        Mockito.verify(productRepository).save(productArgumentCaptor.capture());
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
        Mockito.verify(productRepository).save(productArgumentCaptor.capture());

        Product capturedProduct =
                productArgumentCaptor.getValue();

        assertEquals(capturedProduct, product);
    }


    @Test
    void updateProduct() {
        //given
        underTest.addProduct(product);
        product.setName("L2400 i7");

        //when
        underTest.addProduct(product);

        //when
        p.setBrandName("apple");
        underTest.updateProduct(p);

        //then
        ArgumentCaptor<Product> productArgumentCaptor =
                ArgumentCaptor.forClass(Product.class);
        Mockito.verify(productRepository).save(productArgumentCaptor.capture());

        Product capturedProduct =
                productArgumentCaptor.getValue();
        assertEquals(capturedProduct, product);
    }

    @Test
    void getAllProducts() {
        //when
        underTest.addProduct()
        underTest.getAllProducts();
        //then
        Mockito.verify(productRepository).findAll();
    }

    @Test
    void getAllByBrandName() {
        //when
        String brandName = "apple";
        underTest.getAllByBrandName(brandName);
        //then
        Mockito.verify(productRepository).findAllByBrandName(brandName);
    }

    @Test
    void getAllByCategory() {
        //when
        String category = "laptop";
        underTest.getAllByCategory(category);
        //then
        Mockito.verify(productRepository).findAllByCategory(category);
    }

    @Test
    void getAllByCategoryAndBrandNameAndPriceGreaterThanEqual() {
        //when
        String branName = "apple";
        String category = "laptop";
        Double price = 99.5;
        underTest.getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(category, branName, price);
        //then
        Mockito.verify(productRepository).findAllByCategoryAndBrandNameAndPriceGreaterThan(category, branName, price);

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
        Mockito.verify(productRepository).findAllByPriceGreaterThanEqual(price);
    }

    @Test
    void findAllByPriceLessThanEqual() {
        //when
        Double price = 110.5;
        underTest.getAllByPriceLessThanEqual(price);
        //then
        Mockito.verify(productRepository).findAllByPriceLessThanEqual(price);
    }

    @Test
    void getAllByCategoryAndPriceLessThanEqual() {
        //when
        Double price = 400.20;
        String category = "laptop";
        underTest.getAllByCategoryAndPriceLessThanEqual(category, price);
        //then
        Mockito.verify(productRepository).findAllByCategoryAndPriceLessThanEqual(category, price);
    }

    @Test
    void getAllByCategoryAndPriceGreaterThanEqual() {
        //when
        String category = "mobile";
        Double price = 200.99;
        underTest.getAllByCategoryAndPriceGreaterThanEqual(category, price);
        //then
        Mockito.verify(productRepository).findAllByCategoryAndPriceGreaterThanEqual(category, price);
    }

    @Test
    void getAllByBrandNameAndPriceLessThanEqual() {
        //when
        String brandName = "samsung";
        Double price = 999.99;
        underTest.getAllByBrandNameAndPriceLessThanEqual(brandName, price);
        //then
        Mockito.verify(productRepository).findAllByBrandNameAndPriceLessThanEqual(brandName, price);
    }

    @Test
    void getAllByBrandNameAndPriceGreaterThanEqual() {
        //when
        String brandName ="apple";
        Double price = 300.50;
        underTest.getAllByBrandNameAndPriceGreaterThanEqual(brandName, price);
        //then
        Mockito.verify(productRepository).findAllByBrandNameAndPriceGreaterThanEqual(brandName, price);
    }

    @Test
    void deleteProductById() {
        //when
        int id = 1;
        underTest.deleteProductById(id);
        //then
        Mockito.verify(productRepository).deleteById(id);
    }

    @Test
    void deleteAllProducts() {
        //when
        underTest.deleteAllProducts();
        //then
        Mockito.verify(productRepository).deleteAll();
    }


    @Test
    void testGetProductByName() {
    }

    @Test
    void testAddProduct() {
    }

    @Test
    void testUpdateProduct() {
    }

    @Test
    void testGetAllProducts() {
    }

    @Test
    void testGetAllByBrandName() {
    }

    @Test
    void testGetAllByCategory() {
    }

    @Test
    void testGetAllByCategoryAndBrandNameAndPriceGreaterThanEqual() {
    }

    @Test
    void testGetAllByCategoryAndBrandNameAndPriceLessThanEqual() {
    }

    @Test
    void testGetAllByPriceGreaterThanEqual() {
    }

    @Test
    void getAllByPriceLessThanEqual() {
    }

    @Test
    void testGetAllByCategoryAndPriceLessThanEqual() {
    }

    @Test
    void testGetAllByCategoryAndPriceGreaterThanEqual() {
    }

    @Test
    void testGetAllByBrandNameAndPriceLessThanEqual() {
    }

    @Test
    void testGetAllByBrandNameAndPriceGreaterThanEqual() {
    }

    @Test
    void testDeleteProductById() {
    }

    @Test
    void testDeleteAllProducts() {
    }
}