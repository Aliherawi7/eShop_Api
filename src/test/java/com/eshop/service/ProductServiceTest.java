package com.eshop.service;

import com.eshop.exception.ProductNotFoundException;
import com.eshop.model.Product;
import com.eshop.repository.CommentRepository;
import com.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ProductDTOMapper productDTOMapper;
    @Mock
    private FileStorageService fileStorageService;
    @Mock
    private HttpServletRequest httpServletRequest;
    private ProductService underTest;
    private Product product;


    @BeforeEach
    void setUp() {
        underTest = new ProductService(productRepository,
                commentRepository,
                productDTOMapper,
                fileStorageService,
                httpServletRequest);
        product = new Product(1L, "L2400", "green",
                "dell", "laptop", 599.99,
                "description", "small", 120L, 5d, 10D);
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
        // give
        int offset = 1;
        int pageSize = 10;
        //when
        underTest.getAllProducts(offset, pageSize);
        //then
        verify(productRepository).findAll(PageRequest.of(offset, pageSize));
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
        verify(productRepository).findAllByKeywordsContaining(category);
    }

    @Test
    void getAllByCategoryAndBrandNameAndPriceGreaterThanEqual() {
        //when
        String branName = "apple";
        String category = "laptop";
        Double price = 99.5;
        underTest.getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(category, branName, price);
        //then
        verify(productRepository).findAllByKeywordsContainingAndBrandNameAndPriceGreaterThan(category, branName, price);

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
        verify(productRepository).findAllByKeywordsContainingAndPriceLessThanEqual(category, price);
    }

    @Test
    void getAllByCategoryAndPriceGreaterThanEqual() {
        //when
        String category = "mobile";
        Double price = 200.99;
        underTest.getAllByCategoryAndPriceGreaterThanEqual(category, price);
        //then
        verify(productRepository).findAllByKeywordsContainingAndPriceGreaterThanEqual(category, price);
    }

    @Test
    void getAllByBrandNameAndPriceLessThanEqual() {
        //when
        String brandName = "samsung";
        Double price = 999.99;
        underTest.getAllByBrandNameAndPriceLessThanEqual(brandName, price);
        //then
        verify(productRepository).findAllByBrandNameContainingAndPriceLessThanEqual(brandName, price);
    }

    @Test
    void getAllByBrandNameAndPriceGreaterThanEqual() {
        //when
        String brandName = "apple";
        Double price = 300.50;
        underTest.getAllByBrandNameAndPriceGreaterThanEqual(brandName, price);
        //then
        verify(productRepository).findAllByBrandNameContainingAndPriceGreaterThanEqual(brandName, price);
    }

    @Test
    void deleteProductById() {
        // given
        Long id = 1L;
        //when
        when(productRepository.existsById(id)).thenReturn(true);
        underTest.deleteProductById(id);
        //then
        verify(productRepository).deleteById(id);
    }
    @Test
    void deleteProductByIdIfProductNotExist() {
        // given
        Long id = 1L;
        //when
        when(productRepository.existsById(id)).thenReturn(false);
        ;
        //then
        assertThrows(ProductNotFoundException.class, () -> underTest.deleteProductById(id));
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