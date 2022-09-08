package com.eshop.resources;

import com.eshop.model.Product;
import com.eshop.repository.ProductRepository;
import com.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductResourceTest {
    @Mock private ProductService productService;
    @Mock private ProductRepository productRepository;
    private ProductResource underTest;
    private Product product;

    @BeforeEach
    void setUp() {
        underTest = new ProductResource(productService);
        product = new Product(
                1,"L2400", "red",
                "img", "apple",
                "computer", 99.4,
                "desc", LocalDate.now(),
                "small"
        );
    }

    @Test
    void getAllProducts() {
        //when
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product));
        underTest.getAllProducts();
        // then
        verify(productService).getAllProducts();
        assertEquals(underTest.getAllProducts(), new ResponseEntity<>(underTest.getAllProducts().getBody(), HttpStatus.OK));
    }

    @Test
    void getProduct() {
        //given
        String name = product.getName();
        //when
        when(productService.getProductByName(name)).thenReturn(product);
        underTest.getProduct(name);
        //then
        assertEquals(underTest.getProduct(name), new ResponseEntity<>(product, HttpStatus.OK));
    }

    @Test
    void addProduct() {
        //  when
        when(productService.addProduct(product))
                .thenReturn(new ResponseEntity<>("Successfully saved!", HttpStatus.CREATED));

        //  then
        assertEquals(underTest.addProduct(product), new ResponseEntity<>("Successfully saved!", HttpStatus.CREATED));
        verify(productService).addProduct(product);
    }

    @Test

    // update product if product is exist
    void updateProductIfProductAlreadyExist() {
        // given

        //  when
        when(productService.updateProduct(product))
                .thenReturn(new ResponseEntity<>("Successfully updated! !",HttpStatus.CREATED));

        //  then
        assertEquals(underTest.updateProduct(product),
                new ResponseEntity<>("Successfully updated! !",HttpStatus.CREATED));
        verify(productService).updateProduct(product);
    }

    @Test
    // update product if the product is not exist
    void updateProductIfProductAlreadyNotExist() {
        // given

        //  when
        when(productService.updateProduct(product))
                .thenReturn(new ResponseEntity<>("Successfully updated! !",HttpStatus.CREATED));

        //  then
        assertEquals(underTest.updateProduct(product), new ResponseEntity<>("Successfully updated!",HttpStatus.CREATED));
        verify(productService).updateProduct(product);
    }
    @Test
    void deleteProductById() {
        // given
        int id = product.getId();

        //  when
        when(productService.deleteProductById(id))
                .thenReturn(new ResponseEntity<>("product with id: "+id +" successfully removed", HttpStatus.OK));

        //  then
        assertEquals(underTest.deleteProductById(id),
                new ResponseEntity<>("product with id: "+id +" successfully removed", HttpStatus.OK));
        verify(productService).deleteProductById(id);
    }

    @Test
    void deleteAllProducts() {
        //  when
        when(productService.deleteAllProducts())
                .thenReturn(new ResponseEntity<>("all data removed successfully!", HttpStatus.ACCEPTED));

        //  then
        assertEquals(underTest.deleteAllProducts(),
                new ResponseEntity<>("all data removed successfully!", HttpStatus.ACCEPTED));
        verify(productService).deleteAllProducts();
    }

    @Test
    void getAllByBrandName() {
    }

    @Test
    void getAllByCategory() {
    }

    @Test
    void getAllByPriceGreaterThanEqual() {
    }

    @Test
    void getAllByPriceLessThanEqual() {
    }

    @Test
    void getAllByCategoryAndPriceLessThanEqual() {
    }

    @Test
    void getAllByCategoryAndPriceGreaterThanEqual() {
    }

    @Test
    void getAllByCategoryAndBrandNameAndPriceGreaterThanEqual() {
    }

    @Test
    void getAllByCategoryAndBrandNameAndPriceLessThanEqual() {
    }

    @Test
    void getAllByBrandNameAndPriceLessThanEqual() {
    }

    @Test
    void getAllByBrandNameAndPriceGreaterThanEqual() {
    }
}