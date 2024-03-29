package com.eshop.resources;

import com.eshop.dto.PageContainerDTO;
import com.eshop.dto.ProductDTO;
import com.eshop.repository.ProductRepository;
import com.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductResourceTest {
    @Mock
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    private ProductResource underTest;
    private ProductDTO product;

    @BeforeEach
    void setUp() {
        underTest = new ProductResource(productService);
        product = new ProductDTO();
        product.setName("L2400");
        product.setDiscount(10D);
        product.setProductId(1L);
        product.setBrandName("apple");
        product.setColor("red");
        product.setDetails((ArrayList<String>) Arrays.asList("details".split("\n")));
        product.setSize("small");
        product.setPrice(232);
        product.setCategory("computer");
        product.setRate(4d);
        product.setQuantityInDepot(120);


    }

    @Test
    void getAllProducts() {
        //given
        int offset = 10;
        int pageSize = 1;
        PageContainerDTO pageContainerDTO = new PageContainerDTO(1, Arrays.asList(product));
        //when
        when(productService.getAllProducts(offset, pageSize)).thenReturn(pageContainerDTO);
        underTest.getAllProducts(offset, pageSize);
        // then
        verify(productService).getAllProducts(offset, pageSize);
        assertEquals(underTest.getAllProducts(offset, pageSize), new ResponseEntity<>(underTest.getAllProducts(offset, pageSize).getBody(), HttpStatus.OK));
    }

//    @Test
//    void getProduct() {
//        //given
//        String name = product.getName();
//        //when
//        when(productService.getProductByName(name)).thenReturn(product);
//        underTest.getProduct(name);
//        //then
//        assertEquals(underTest.getProduct(name), new ResponseEntity<>(product, HttpStatus.OK));
//    }
/*
    @Test
    @Disabled
    void addProduct() {
        //  when
        when(productService.addProduct(product))
                .thenReturn(new ResponseEntity<>("Successfully saved!", HttpStatus.CREATED));

        //  then
        assertEquals(underTest.addProduct(product), new ResponseEntity<>("Successfully saved!", HttpStatus.CREATED));
        verify(productService).addProduct(product);
    }*/

    @Test

        // update product if product is exist
    void updateProductIfProductAlreadyExist() {
        // given

        //  when
//        when(productService.updateProduct(product))
//                .thenReturn(new ResponseEntity<>("Successfully updated! !", HttpStatus.CREATED));
//
//        //  then
//        assertEquals(underTest.updateProduct(product),
//                new ResponseEntity<>("Successfully updated! !", HttpStatus.CREATED));
//        verify(productService).updateProduct(product);
    }

    @Test
        // update product if the product is not exist
    void updateProductIfProductAlreadyNotExist() {
        // given

        //  when
//        when(productService.updateProduct(product))
//                .thenReturn(new ResponseEntity<>("not Found such product with id: " + product.getId(), HttpStatus.NOT_FOUND));
//
//        //  then
//        assertEquals(underTest.updateProduct(product),
//                new ResponseEntity<>("not Found such product with id: " + product.getId(), HttpStatus.NOT_FOUND));
//        verify(productService).updateProduct(product);
    }

    @Test
    void deleteProductById() {
        // given
        Long id = product.getProductId();

        //  when
        when(productService.deleteProductById(id))
                .thenReturn(new ResponseEntity<>("product with id: " + id + " successfully removed", HttpStatus.OK));

        //  then
        assertEquals(underTest.deleteProductById(id),
                new ResponseEntity<>("product with id: " + id + " successfully removed", HttpStatus.OK));
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

//    @Test
//        //if products with the brand name are exist
//    void getAllByBrandNameIfProductsWithTheBrandNameExist() {
//        //  given
//        String brandName = product.getBrandName();
//        Collection<Product> products = Arrays.asList(product);
//        //  when
//        when(productService.getAllByBrandName(brandName)).thenReturn(products);
//
//        //  then
//        assertEquals(underTest.getAllByBrandName(brandName), new ResponseEntity<>(products, HttpStatus.OK));
//        verify(productService).getAllByBrandName(brandName);
//    }

    @Test
        //if products with the brand name are not exist
    void getAllByBrandNameIfProductsWithTheBrandNameNotExist() {
        //  given
        String brandName = product.getBrandName();
        Collection<ProductDTO> products = new ArrayList<>();
        //  when
        when(productService.getAllByBrandName(brandName))
                .thenReturn(null);

        //  then
        assertEquals(underTest.getAllByBrandName(brandName),
                new ResponseEntity<>("no products found by brand name: " + brandName, HttpStatus.NOT_FOUND));
        verify(productService).getAllByBrandName(brandName);
    }

//    @Test
//    void getAllByCategory() {
//        //  given
//        String category = product.getCategory();
//        Collection<Product> products = Arrays.asList(product);
//
//        //  when
//        when(productService.getAllByCategory(category)).thenReturn(products);
//
//        //  then
//        assertEquals(underTest.getAllByCategory(category), new ResponseEntity<>(products, HttpStatus.OK));
//        verify(productService).getAllByCategory(category);
//    }

//    @Test
//        //if products exist by price greater or equal to the minPrice
//    void getAllByPriceGreaterThanEqualIfExist() {
//        //  given
//        double minPrice = product.getPrice();
//        Collection<Product> products = Arrays.asList(productDTO);
//
//        //  when
//        when(productService.getAllByPriceGreaterThanEqual(minPrice))
//                .thenReturn(products);
//
//        //  then
//        assertEquals(underTest.getAllByPriceGreaterThanEqual(minPrice),
//                new ResponseEntity<>(products, HttpStatus.OK));
//        verify(productService).getAllByPriceGreaterThanEqual(minPrice);
//    }

    @Test
        //if products exist by price greater or equal to the minPrice
    void getAllByPriceGreaterThanEqualIfNotExist() {
        //  given
        double minPrice = product.getPrice();
        Collection<ProductDTO> products = new ArrayList<>();

        //  when
        when(productService.getAllByPriceGreaterThanEqual(minPrice))
                .thenReturn(null);

        //  then
        assertEquals(underTest.getAllByPriceGreaterThanEqual(minPrice),
                new ResponseEntity<>("no product found by price greater than or equal to : " + minPrice, HttpStatus.NOT_FOUND));
        verify(productService).getAllByPriceGreaterThanEqual(minPrice);
    }

//    @Test
//        //  if product exist with the specific price
//    void getAllByPriceLessThanEqualIfExist() {
//        //  given
//        double maxPrice = product.getPrice();
//        Collection<ProductDTO> products = Collections.singletonList(product);
//
//        //  when
//        when(productService.getAllByPriceLessThanEqual(maxPrice))
//                .thenReturn(products);
//
//        //  then
//        assertEquals(underTest.getAllByPriceLessThanEqual(maxPrice),
//                new ResponseEntity<>(products, HttpStatus.OK));
//        verify(productService).getAllByPriceLessThanEqual(maxPrice);
//    }

    @Test
        //  if product not exist with the specific price
    void getAllByPriceLessThanEqualIfNotExist() {
        //  given
        double maxPrice = product.getPrice();
        Collection<ProductDTO> products = new ArrayList<>();

        //  when
        when(productService.getAllByPriceLessThanEqual(maxPrice))
                .thenReturn(null);

        //  then
        assertEquals(underTest.getAllByPriceLessThanEqual(maxPrice),
                new ResponseEntity<>("no product found by price less than or equal to : " + maxPrice, HttpStatus.NOT_FOUND));
        verify(productService).getAllByPriceLessThanEqual(maxPrice);
    }

//    @Test
//        //  if products exist with the specific category name and price
//    void getAllByCategoryAndPriceLessThanEqualIfExist() {
//        //  given
//        String category = product.getCategory();
//        double maxPrice = product.getPrice();
//        Collection<Product> products = Arrays.asList(product);
//
//        //  when
//        when(productService.getAllByCategoryAndPriceLessThanEqual(category, maxPrice))
//                .thenReturn(products);
//        Map<String, String> params = new HashMap<>();
//        params.put("category", category);
//        params.put("maxprice", maxPrice + "");
//
//        //  then
//        assertEquals(underTest.getAllByCategoryAndPriceLessThanEqual(params),
//                new ResponseEntity<>(products, HttpStatus.OK));
//        verify(productService).getAllByCategoryAndPriceLessThanEqual(category, maxPrice);
//    }

    @Test
        //  if products not exist with the specific category name and price
    void getAllByCategoryAndPriceLessThanEqualIfNotExist() {
        //  given
        String category = product.getCategory();
        double maxPrice = product.getPrice();
        Collection<ProductDTO> products = new ArrayList<>();

        //  when
        when(productService.getAllByCategoryAndPriceLessThanEqual(category, maxPrice))
                .thenReturn(null);
        Map<String, String> params = new HashMap<>();
        params.put("category", category);
        params.put("maxprice", maxPrice + "");

        //  then
        assertEquals(underTest.getAllByCategoryAndPriceLessThanEqual(params),
                new ResponseEntity<>("no product found by category: " + category + "" +
                        " and price less than or equal to : " + maxPrice, HttpStatus.NOT_FOUND));
        verify(productService).getAllByCategoryAndPriceLessThanEqual(category, maxPrice);
    }

//    @Test
//        //  if products exist with the category and minPrice
//    void getAllByCategoryAndPriceGreaterThanEqualIfExist() {
//        // given
//        String category = product.getCategory();
//        double minPrice = product.getPrice();
//        Collection<Product> products = Arrays.asList(product);
//
//        //  when
//        when(productService.getAllByCategoryAndPriceGreaterThanEqual(category, minPrice))
//                .thenReturn(products);
//        Map<String, String> params = new HashMap<>();
//        params.put("category", category);
//        params.put("minprice", minPrice + "");
//
//        //  then
//        assertEquals(underTest.getAllByCategoryAndPriceGreaterThanEqual(params),
//                new ResponseEntity<>(products, HttpStatus.OK));
//        verify(productService).getAllByCategoryAndPriceGreaterThanEqual(category, minPrice);
//    }

    @Test
        //  if products not exist with the category and minPrice
    void getAllByCategoryAndPriceGreaterThanEqualIfNotExist() {
        //  given
        String category = product.getCategory();
        double minPrice = product.getPrice();
        Collection<ProductDTO> products = new ArrayList<>();

        //  when
        when(productService.getAllByCategoryAndPriceGreaterThanEqual(category, minPrice))
                .thenReturn(null);
        Map<String, String> params = new HashMap<>();
        params.put("category", category);
        params.put("minprice", minPrice + "");

        //  then
        assertEquals(underTest.getAllByCategoryAndPriceGreaterThanEqual(params),
                new ResponseEntity<>("no product found by category: " + category
                        + " and price greater than or equal to : " + minPrice, HttpStatus.NOT_FOUND));
        verify(productService).getAllByCategoryAndPriceGreaterThanEqual(category, minPrice);
    }

//    @Test
//        //  if product exist with the specific category, brand name and price
//    void getAllByCategoryAndBrandNameAndPriceGreaterThanEqualIfExist() {
//        //given
//        String category = product.getCategory();
//        String brandName = product.getBrandName();
//        double minPrice = product.getPrice();
//        Collection<Product> products = Arrays.asList(product);
//
//        //  when
//        when(productService.getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(category, brandName, minPrice))
//                .thenReturn(products);
//        Map<String, String> params = new HashMap<>();
//        params.put("category", category);
//        params.put("brand", brandName);
//        params.put("minprice", minPrice + "");
//
//        //  then
//        assertEquals(underTest.getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(params),
//                new ResponseEntity<>(products, HttpStatus.OK));
//        verify(productService)
//                .getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(category, brandName, minPrice);
//    }

    @Test
        //  if products not exist with the specific category, brand name and price
    void getAllByCategoryAndBrandNameAndPriceGreaterThanEqualIfNotExist() {
        //given
        String category = product.getCategory();
        String brandName = product.getBrandName();
        double minPrice = product.getPrice();
        Collection<ProductDTO> products = new ArrayList<>();

        //  when
        when(productService.getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(category, brandName, minPrice))
                .thenReturn(null);
        Map<String, String> params = new HashMap<>();
        params.put("category", category);
        params.put("brand", brandName);
        params.put("minprice", minPrice + "");

        //  then
        assertEquals(underTest.getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(params),
                new ResponseEntity<>
                        ("no product found by category: " + category + ", " +
                                "brand name: " + brandName + ", price: " + minPrice, HttpStatus.NOT_FOUND));
        verify(productService)
                .getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(category, brandName, minPrice);
    }

//    @Test
//        //  if products exist with the specific category, brand name and price
//    void getAllByCategoryAndBrandNameAndPriceLessThanEqualIfExist() {
//        //given
//        String category = product.getCategory();
//        String brandName = product.getBrandName();
//        double maxPrice = product.getPrice();
//        Collection<Product> products = Arrays.asList(product);
//
//        //  when
//        when(productService.getAllByCategoryAndBrandNameAndPriceLessThanEqual(category, brandName, maxPrice))
//                .thenReturn(products);
//        Map<String, String> params = new HashMap<>();
//        params.put("category", category);
//        params.put("brand", brandName);
//        params.put("maxprice", maxPrice + "");
//
//        //  then
//        assertEquals(underTest.getAllByCategoryAndBrandNameAndPriceLessThanEqual(params),
//                new ResponseEntity<>(products, HttpStatus.OK));
//        verify(productService)
//                .getAllByCategoryAndBrandNameAndPriceLessThanEqual(category, brandName, maxPrice);
//    }

    @Test
        //  if products not exist with the specific category, brand name and price
    void getAllByCategoryAndBrandNameAndPriceLessThanEqualIfNotExist() {
        //given
        String category = product.getCategory();
        String brandName = product.getBrandName();
        double maxPrice = product.getPrice();
        Collection<ProductDTO> products = new ArrayList<>();

        //  when
        when(productService.getAllByCategoryAndBrandNameAndPriceLessThanEqual(category, brandName, maxPrice))
                .thenReturn(null);
        Map<String, String> params = new HashMap<>();
        params.put("category", category);
        params.put("brand", brandName);
        params.put("maxprice", maxPrice + "");

        //  then
        assertEquals(underTest.getAllByCategoryAndBrandNameAndPriceLessThanEqual(params),
                new ResponseEntity<>
                        ("no product found by category: " + category + ", " +
                                "brand name: " + brandName + ", price less than or equal: " + maxPrice, HttpStatus.NOT_FOUND));
        verify(productService)
                .getAllByCategoryAndBrandNameAndPriceLessThanEqual(category, brandName, maxPrice);
    }
//
//    @Test
//        //  if products exist with specific brand name and price
//    void getAllByBrandNameAndPriceLessThanEqualIfExist() {
//        //  given
//        String brandName = product.getBrandName();
//        double maxPrice = product.getPrice();
//        Collection<Product> products = Arrays.asList(product);
//
//        //  when
//        when(productService.getAllByBrandNameAndPriceLessThanEqual(brandName, maxPrice))
//                .thenReturn(products);
//        Map<String, String> params = new HashMap<>();
//        params.put("brand", brandName);
//        params.put("maxprice", maxPrice + "");
//
//        //  then
//        assertEquals(underTest.getAllByBrandNameAndPriceLessThanEqual(params),
//                new ResponseEntity<>(products, HttpStatus.OK));
//        verify(productService).getAllByBrandNameAndPriceLessThanEqual(brandName, maxPrice);
//    }

    @Test
        //  if products not exist with specific brand name and price
    void getAllByBrandNameAndPriceLessThanEqualIfNotExist() {
        //  given
        String brandName = product.getBrandName();
        double maxPrice = product.getPrice();
        Collection<ProductDTO> products = new ArrayList<>();

        //  when
        when(productService.getAllByBrandNameAndPriceLessThanEqual(brandName, maxPrice))
                .thenReturn(null);
        Map<String, String> params = new HashMap<>();
        params.put("brand", brandName);
        params.put("maxprice", maxPrice + "");

        //  then
        assertEquals(underTest.getAllByBrandNameAndPriceLessThanEqual(params),
                new ResponseEntity<>
                        ("no product found by brand name: " + brandName + " and price less than or equal to : " + maxPrice, HttpStatus.NOT_FOUND));
        verify(productService).getAllByBrandNameAndPriceLessThanEqual(brandName, maxPrice);
    }

//    @Test
//        //  if products exist with specific brand name and price Greater than or equal
//    void getAllByBrandNameAndPriceGreaterThanEqualIfExist() {
//        //  given
//        String brandName = product.getBrandName();
//        double minPrice = product.getPrice();
//        Collection<Product> products = Arrays.asList(product);
//
//        //  when
//        when(productService.getAllByBrandNameAndPriceGreaterThanEqual(brandName, minPrice))
//                .thenReturn(products);
//        Map<String, String> params = new HashMap<>();
//        params.put("brand", brandName);
//        params.put("minprice", minPrice + "");
//
//        //  then
//        assertEquals(underTest.getAllByBrandNameAndPriceGreaterThanEqual(params),
//                new ResponseEntity<>(products, HttpStatus.OK));
//        verify(productService).getAllByBrandNameAndPriceGreaterThanEqual(brandName, minPrice);
//    }

    @Test
        //  if products not exist with specific brand name and price Greater than or equal
    void getAllByBrandNameAndPriceGreaterThanEqualIfNotExist() {
        //  given
        String brandName = product.getBrandName();
        double minPrice = product.getPrice();
        Collection<ProductDTO> products = new ArrayList<>();

        //  when
        when(productService.getAllByBrandNameAndPriceGreaterThanEqual(brandName, minPrice))
                .thenReturn(null);
        Map<String, String> params = new HashMap<>();
        params.put("brand", brandName);
        params.put("minprice", minPrice + "");

        //  then
        assertEquals(underTest.getAllByBrandNameAndPriceGreaterThanEqual(params),
                new ResponseEntity<>
                        ("no product found by brand name: " + brandName + " and price greater than or equal to : " + minPrice, HttpStatus.NOT_FOUND));
        verify(productService).getAllByBrandNameAndPriceGreaterThanEqual(brandName, minPrice);
    }
}