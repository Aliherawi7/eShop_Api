package com.eshop.resources;

import com.eshop.dto.ProductDTO;
import com.eshop.dto.ProductRegistrationRequest;
import com.eshop.dto.ProductUpdateRequest;
import com.eshop.model.Product;
import com.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * @Author: Aliherawi
 * @Github: Aliherawi7
 * @linkedin: ali-herawi
 * */
@RestController
@RequestMapping("/api/products")
public class ProductResource {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    //get all the product from database
    @GetMapping("pagination/{offset}/{pageSize}")
    public ResponseEntity<?> getAllProducts(@PathVariable int offset, @PathVariable int pageSize) {
        return ResponseEntity.ok().body(productService.getAllProducts(offset, pageSize));
    }

    //find by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        if (productDTO != null) {
            return ResponseEntity.ok().body(productDTO);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    // save the product in database
    @PostMapping
    public ResponseEntity<?> addProduct(@ModelAttribute ProductRegistrationRequest request) {
        return new ResponseEntity<>(productService.addProduct(request), HttpStatus.CREATED);
    }

    //update th existed product
    @PutMapping()
    public ResponseEntity<String> updateProduct(@ModelAttribute ProductUpdateRequest request) {
        boolean isUpdated = productService.updateProduct(request);
        if(isUpdated){
            return ResponseEntity.ok().body("product successfully updated");
        }
        return ResponseEntity.badRequest().body("product not found!");
    }

    //remove product by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        System.out.println(id);
        System.out.println("product deleted");
        return productService.deleteProductById(id);
    }

    //remove all products
    @DeleteMapping
    public ResponseEntity<?> deleteAllProducts() {
        return productService.deleteAllProducts();
    }


    /*===========================================*
     *============   query methods   ============*
     *===========================================*/
    // find product by name
    @GetMapping(value = "pagination/{offset}/{pageSize}/find", params = {"name"})
    public ResponseEntity<?> searchProducts(@PathVariable int offset, @PathVariable int pageSize, @RequestParam String all) {
        return ResponseEntity.ok(productService.searchProducts(offset, pageSize, all));
    }

    // find product by name
    @GetMapping(value = "/find", params = {"keyword"})
    public ResponseEntity<?> search(@RequestParam String keyword) {
        return ResponseEntity.ok().body(productService.getAllProductByNameContainingOrCategoryContainingOrBrandNameContaining(keyword));
    }

    // search products
    @GetMapping(value = "/find", params = {"name"})
    public ResponseEntity<?> getProduct(@RequestParam String name) {
        return ResponseEntity.ok().body(productService.getAllProductByNameContaining(name));
    }

    // find by brand name
    @GetMapping(value = "/find", params = {"brand"})
    public ResponseEntity<?> getAllByBrandName(@RequestParam String brand) {
        Collection<ProductDTO> products = productService.getAllByBrandName(brand);
        if (products.size() > 0) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("no products found by brand name: " + brand, HttpStatus.NOT_FOUND);
        }
    }

    // find product by category
    @GetMapping(value = "/find", params = {"category"})
    public ResponseEntity<?> getAllByCategory(@RequestParam String category) {
        return ResponseEntity.ok(productService.getAllByCategory(category));
    }

    // find products by price greater than or equal to the given price
    @GetMapping(value = "/find", params = {"minprice"})
    public ResponseEntity<?> getAllByPriceGreaterThanEqual(@RequestParam Double minprice) {
        return ResponseEntity.ok(productService.getAllByPriceGreaterThanEqual(minprice));
    }

    // find products by price Less than or equal to the given price
    @GetMapping(value = "/find", params = {"maxprice"})
    public ResponseEntity<?> getAllByPriceLessThanEqual(@RequestParam Double maxprice) {
        return ResponseEntity.ok(productService.getAllByPriceLessThanEqual(maxprice));
    }

    // find Products by category and price less than or equal to price
    @GetMapping(value = "/find", params = {"category", "maxprice"})
    public ResponseEntity<?> getAllByCategoryAndPriceLessThanEqual
    (@RequestParam Map<String, String> params) {
        String category = params.get("category");
        Double maxPrice = Double.parseDouble(params.get("maxprice"));
        return ResponseEntity.ok(productService.getAllByCategoryAndPriceLessThanEqual(category, maxPrice));

    }

    // find Products by category and price greater than or equal to price
    @GetMapping(value = "/find", params = {"category", "minprice"})
    public ResponseEntity<?> getAllByCategoryAndPriceGreaterThanEqual
    (@RequestParam Map<String, String> params) {
        String category = params.get("category");
        Double minprice = Double.parseDouble(params.get("minprice"));
        return ResponseEntity.ok(productService.getAllByCategoryAndPriceGreaterThanEqual(category, minprice));
    }

    // find products by category, brand name and greater or equal to the price
    @GetMapping(value = "/find", params = {"category", "brand", "minprice"})
    public ResponseEntity<?> getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(@RequestParam Map<String, String> params) {
        String category = params.get("category");
        String brandName = params.get("brand");
        Double minPrice = Double.parseDouble(params.get("minprice"));
        return ResponseEntity.ok(productService
                .getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(category, brandName, minPrice));
    }

    // find products by category, brand name and less than or equal to the price
    @GetMapping(value = "/find", params = {"category", "brand", "maxprice"})
    public ResponseEntity<?> getAllByCategoryAndBrandNameAndPriceLessThanEqual(@RequestParam Map<String, String> params) {
        String category = params.get("category");
        String brandName = params.get("brand");
        Double maxPrice = Double.parseDouble(params.get("maxprice"));
        return ResponseEntity.ok(
                productService.getAllByCategoryAndBrandNameAndPriceLessThanEqual(category, brandName, maxPrice));
    }


    // find all product by brand name and price less than or equal to the given price
    @GetMapping(value = "/find", params = {"brand", "maxprice"})
    public ResponseEntity<?> getAllByBrandNameAndPriceLessThanEqual(@RequestParam Map<String, String> params) {
        String brandName = params.get("brand");
        Double maxPrice = Double.parseDouble(params.get("maxprice"));
        return ResponseEntity.ok(productService.getAllByBrandNameAndPriceLessThanEqual(brandName, maxPrice));
    }

    // find all product by brand name and price greater than or equal to the given price
    @GetMapping(value = "/find", params = {"brand", "minprice"})
    public ResponseEntity<?> getAllByBrandNameAndPriceGreaterThanEqual(@RequestParam Map<String, String> params) {
        String brandName = params.get("brand");
        Double minPrice = Double.parseDouble(params.get("minprice"));
        return ResponseEntity.ok(productService.getAllByBrandNameAndPriceGreaterThanEqual(brandName, minPrice));
    }


}
