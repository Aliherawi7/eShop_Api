package com.eshop.resources;

import com.eshop.model.Product;
import com.eshop.service.ProductService;
import com.sun.deploy.net.proxy.pac.PACFunctions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

/*
 * @Author: Aliherawi
 * @Github: Aliherawi7
 * @linkedin: ali-herawi
 * */
@RestController
@RequestMapping("/api/products")
public class ProductResource {

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    //get all the product from database
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        Collection<Product> products = productService.getAllProducts();
        if (products.size() > 0) {
            return ResponseEntity.ok().body(products);
        } else {
            return new ResponseEntity<String>("No Product is available!", HttpStatus.NOT_FOUND);
        }
    }

    //find by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // save the product in database
    @PostMapping(value = "/save")
    public ResponseEntity<?> addProduct(@RequestParam("image")MultipartFile file, @RequestParam Map<String, String> param) throws IOException {
        byte[] bytes = file.getBytes();
        Product product = new Product(
                null,
                param.get("name"),
                param.get("color"),
                bytes,
                param.get("brandName"),
                param.get("category"),
                Double.parseDouble(param.get("price")),
                param.get("description"),
                LocalDate.parse(param.get("productionDate")),
                param.get("size"),
                Long.parseLong(param.get("quantityInDepot")),
                Double.parseDouble(param.get("rate")),
                Double.parseDouble(param.get("discount"))
        );
        productService.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }

    //update th existed product
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") Long id, @RequestParam("image") MultipartFile image, @RequestParam Map<String, String> params) {
        Product product = new Product();
        product.setId(id);
        product.setName(params.get("name"));
        product.setCategory(params.get("category"));
        product.setBrandName(params.get("brandName"));
        product.setPrice(Double.parseDouble(params.get("price")));
        product.setProductionDate(LocalDate.parse(params.get("productionDate")));
        product.setColor(params.get("color"));
        product.setDescription(params.get("description"));
        product.setQuantityInDepot(Long.parseLong(params.get("quantityInDepot")));
        product.setSize(params.get("size"));
        try {
            product.setImage(image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productService.updateProduct(product);
    }

    //remove product by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
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
    @GetMapping(value = "/find", params = {"name"})
    public ResponseEntity<?> getProduct(@RequestParam String name) {
        Product product = productService.getProductByName(name);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("not product found by name : " + name, HttpStatus.NOT_FOUND);
        }
    }

    // find by brand name
    @GetMapping(value = "/find", params = {"brand"})
    public ResponseEntity<?> getAllByBrandName(@RequestParam String brand) {
        Collection<Product> products = productService.getAllByBrandName(brand);
        if (products.size() > 0) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("no products found by brand name: " + brand, HttpStatus.NOT_FOUND);
        }
    }

    // find product by category
    @GetMapping(value = "/find", params = {"category"})
    public ResponseEntity<?> getAllByCategory(@RequestParam String category) {
        Collection<Product> products = productService.getAllByCategory(category);
        if (products.size() > 0) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("no product found by category: " + category, HttpStatus.NOT_FOUND);
        }
    }

    // find products by price greater than or equal to the given price
    @GetMapping(value = "/find", params = {"minprice"})
    public ResponseEntity<?> getAllByPriceGreaterThanEqual(@RequestParam Double minprice) {
        Collection<Product> products = productService.getAllByPriceGreaterThanEqual(minprice);
        if (products.size() > 0) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("no product found by price greater than or equal to : " + minprice, HttpStatus.NOT_FOUND);
        }
    }

    // find products by price Less than or equal to the given price
    @GetMapping(value = "/find", params = {"maxprice"})
    public ResponseEntity<?> getAllByPriceLessThanEqual(@RequestParam Double maxprice) {
        Collection<Product> products = productService.getAllByPriceLessThanEqual(maxprice);
        if (products.size() > 0) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("no product found by price less than or equal to : " + maxprice, HttpStatus.NOT_FOUND);
        }
    }

    // find Products by category and price less than or equal to price
    @GetMapping(value = "/find", params = {"category", "maxprice"})
    public ResponseEntity<?> getAllByCategoryAndPriceLessThanEqual
    (@RequestParam Map<String, String> params) {
        String category = params.get("category");
        Double maxprice = Double.parseDouble(params.get("maxprice"));

        Collection<Product> products = productService.getAllByCategoryAndPriceLessThanEqual(category, maxprice);
        if (products.size() > 0) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("no product found by category: " + category + " and price less than or equal to : " + maxprice, HttpStatus.NOT_FOUND);
        }
    }

    // find Products by category and price greater than or equal to price
    @GetMapping(value = "/find", params = {"category", "minprice"})
    public ResponseEntity<?> getAllByCategoryAndPriceGreaterThanEqual
    (@RequestParam Map<String, String> params) {
        String category = params.get("category");
        Double minprice = Double.parseDouble(params.get("minprice"));
        Collection<Product> products = productService.getAllByCategoryAndPriceGreaterThanEqual(category, minprice);
        if (products.size() > 0) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("no product found by category: " + category + " and price greater than or equal to : " + minprice, HttpStatus.NOT_FOUND);
        }
    }

    // find products by category, brand name and greater or equal to the price
    @GetMapping(value = "/find", params = {"category", "brand", "minprice"})
    public ResponseEntity<?> getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(@RequestParam Map<String, String> params) {
        String category = params.get("category");
        String brandName = params.get("brand");
        Double minprice = Double.parseDouble(params.get("minprice"));
        Collection<Product> products =
                productService.getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(category, brandName, minprice);
        if (products.size() > 0) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>
                    ("no product found by category: " + category + ", " +
                            "brand name: " + brandName + ", price: " + minprice, HttpStatus.NOT_FOUND);
        }
    }

    // find products by category, brand name and less than or equal to the price
    @GetMapping(value = "/find", params = {"category", "brand", "maxprice"})
    public ResponseEntity<?> getAllByCategoryAndBrandNameAndPriceLessThanEqual(@RequestParam Map<String, String> params) {
        String category = params.get("category");
        String brandName = params.get("brand");
        Double maxprice = Double.parseDouble(params.get("maxprice"));
        Collection<Product> products =
                productService.getAllByCategoryAndBrandNameAndPriceLessThanEqual(category, brandName, maxprice);
        if (products.size() > 0) return new ResponseEntity<>(products, HttpStatus.OK);
        else return new ResponseEntity<>
                ("no product found by category: " + category + ", " +
                        "brand name: " + brandName + ", price less than or equal: " + maxprice, HttpStatus.NOT_FOUND);
    }


    // find all product by brand name and price less than or equal to the given price
    @GetMapping(value = "/find", params = {"brand", "maxprice"})
    public ResponseEntity<?> getAllByBrandNameAndPriceLessThanEqual(@RequestParam Map<String, String> params) {
        String brandName = params.get("brand");
        Double maxprice = Double.parseDouble(params.get("maxprice"));
        Collection<Product> products = productService.getAllByBrandNameAndPriceLessThanEqual(brandName, maxprice);
        if (products.size() > 0) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>
                    ("no product found by brand name: " + brandName + " and price less than or equal to : " + maxprice, HttpStatus.NOT_FOUND);
        }
    }

    // find all product by brand name and price greater than or equal to the given price
    @GetMapping(value = "/find", params = {"brand", "minprice"})
    public ResponseEntity<?> getAllByBrandNameAndPriceGreaterThanEqual(@RequestParam Map<String, String> params) {
        String brandName = params.get("brand");
        Double minprice = Double.parseDouble(params.get("minprice"));
        Collection<Product> products = productService.getAllByBrandNameAndPriceGreaterThanEqual(brandName, minprice);
        if (products.size() > 0) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>
                    ("no product found by brand name: " + brandName + " and price greater than or equal to : " + minprice, HttpStatus.NOT_FOUND);
        }
    }


}
