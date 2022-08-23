package com.eshop.resources;

import com.eshop.model.Product;
import com.eshop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ProductResource(ProductService productService){
        this.productService = productService;
    }
    //get all the product from database
    @GetMapping
    public ResponseEntity<?> getAllProducts(){
        Collection<Product> products = productService.getAllProducts();
        if(products.size() > 0){
            return ResponseEntity.ok().body(products);
        }else{
            return new ResponseEntity<String>("No Product is available!", HttpStatus.NOT_FOUND);
        }
    }
    // find product by name
    @GetMapping(value = "/find/name={name}" )
    public ResponseEntity<?> getProduct(@PathVariable String name){
        Product product = productService.getProductByName(name);
        if(product != null){
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("not product found by name : "+name, HttpStatus.NOT_FOUND);
        }
    }

    // save the product in database
    @PostMapping("/save")
    public ResponseEntity<?> addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    //update th existed product
    @PutMapping("update")
    public ResponseEntity<String> updateProduct(@RequestBody Product product){
        if(product.getId() == null){
            return new ResponseEntity<>("error_message: the id should not be null!", HttpStatus.BAD_REQUEST);
        }
        return productService.updateProduct(product);
    }

    //remove product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Integer id){
        boolean isDeleted = productService.deleteProductById(id);
        if(isDeleted){
            return new ResponseEntity<>("Product with id: "+ id +" successfully removed", HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("Not found any product by id: "+id, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping
    public ResponseEntity<?> deleteAllProducts(){
        return productService.deleteAllProducts();
    }


    /*===========================================*
     *============   query methods   ============*
     *===========================================*/

    // find by brand name
    @GetMapping(value = "/brand/", params = {"brand"})
    public ResponseEntity<?> getAllByBrandName(@RequestParam String brand){
        Collection<Product> products = productService.getAllByBrandName(brand);
        if(products.size()>0){
            return new ResponseEntity<>(products, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no products found by brand name: "+ brand, HttpStatus.NOT_FOUND);
        }
    }

    // find product by category
    @GetMapping(value = "/category/", params = {"category"})
    public ResponseEntity<?> getAllByCategory(@RequestParam String category){
        Collection<Product> products = productService.getAllByCategory(category);
        if(products.size()>0){
            return new ResponseEntity<>(products, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no product found by category: " + category, HttpStatus.NOT_FOUND);
        }
    }

    // find products by price greater than or equal to the given price
    @GetMapping(value = "/price/", params = {"minprice"})
    public ResponseEntity<?> getAllByPriceGreaterThanEqual(@RequestParam Double minprice){
        Collection<Product> products = productService.getAllByPriceGreaterThanEqual(minprice);
        if(products.size()>0){
            return new ResponseEntity<>(products, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no product found by price greater than or equal to : "+minprice, HttpStatus.NOT_FOUND);
        }
    }

    // find products by price Less than or equal to the given price
    @GetMapping(value = "/price/", params = {"maxprice"})
    public ResponseEntity<?> getAllByPriceLessThanEqual(@RequestParam Double maxprice){
        Collection<Product> products = productService.findAllByPriceLessThanEqual(maxprice);
        if(products.size()>0){
            return new ResponseEntity<>(products, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no product found by price less than or equal to : "+maxprice, HttpStatus.NOT_FOUND);
        }
    }

    // find Products by category and price less than or equal to price
    @GetMapping(value = "/find", params = {"category", "maxprice"})
    public ResponseEntity<?> getAllByCategoryAndPriceLessThanEqual
    (@RequestParam Map<String, String> params){
        String category = params.get("category");
        Double maxprice = Double.parseDouble(params.get("maxprice"));
        System.err.println("category: "+category+ " price: "+maxprice);
        Collection<Product> products = productService.getAllByCategoryAndPriceLessThanEqual(category, maxprice);
        if(products.size() > 0){
            return new ResponseEntity<>(products, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no product found by category: "+category+" and price less than or equal to : "+maxprice, HttpStatus.NOT_FOUND);
        }
    }

    // find Products by category and price greater than or equal to price
    @GetMapping(value = "/find",params = {"category", "minprice"})
    public ResponseEntity<?> getAllByCategoryAndPriceGreaterThanEqual
    (@RequestParam Map<String, String> params){
        String category = params.get("category");
        Double minprice = Double.parseDouble(params.get("minprice"));
        System.err.println("category: "+category+ " minprice: "+minprice);
        Collection<Product> products = productService.getAllByCategoryAndPriceGreaterThanEqual(category, minprice);
        if(products.size() > 0){
            return new ResponseEntity<>(products, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no product found by category: "+category+" and price greater than or equal to : "+minprice, HttpStatus.NOT_FOUND);
        }
    }

    // find products by category, brand name and greater or equal to the price
    @GetMapping(value = "/find", params = {"category", "brand", "minprice"})
    public ResponseEntity<?> getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(@RequestParam Map<String, String> params){
        String category = params.get("category");
        String brandName = params.get("brand");
        Double minprice = Double.parseDouble(params.get("minprice"));
        System.err.println("category: "+category+ " minprice: "+minprice+" brandName: "+brandName);
        Collection<Product> products =
                productService.getAllByCategoryAndBrandNameAndPriceGreaterThanEqual(category, brandName, minprice);
        if(products.size() > 0){
            return new ResponseEntity<>(products, HttpStatus.OK);
        }else{
            return new ResponseEntity<>
                    ("no product found by category: "+category+", " +
                            "brand name: "+brandName+ ", price: "+minprice, HttpStatus.NOT_FOUND);
        }
    }

    // find products by category, brand name and less than or equal to the price
    @GetMapping(value = "/find", params = {"category", "brand","maxprice"})
    public ResponseEntity<?> getAllByCategoryAndBrandNameAndPriceLessThanEqual(@RequestParam Map<String, String> params){
        String category = params.get("category");
        String brandName = params.get("brand");
        Double maxprice = Double.parseDouble(params.get("maxprice"));
        System.err.println("category: "+category+ " price: "+maxprice+" brandName: "+brandName);
        Collection<Product> products =
                productService.getAllByCategoryAndBrandNameAndPriceLessThanEqual(category, brandName, maxprice);
        if(products.size() > 0) return new ResponseEntity<>(products, HttpStatus.OK);
        else return new ResponseEntity<>
                ("no product found by category: " + category + ", " +
                        "brand name: " + brandName + ", price less than or equal: " + maxprice, HttpStatus.NOT_FOUND);
    }


    // find all product by brand name and price less than or equal to the given price
    @GetMapping(value = "/find", params = {"brand","maxprice"})
    public ResponseEntity<?> getAllByBrandNameAndPriceLessThanEqual(@RequestParam Map<String, String> params){
        String brandName = params.get("brand");
        Double maxprice = Double.parseDouble(params.get("maxprice"));
        System.err.println(" price: "+maxprice+" brandName: "+brandName);
        Collection<Product> products = productService.getAllByBrandNameAndPriceLessThanEqual(brandName, maxprice);
        if(products.size() > 0){
            return new ResponseEntity<>(products, HttpStatus.OK);
        }else{
            return new ResponseEntity<>
                    ("no product found by brand name: "+brandName+" and price less than or equal to : "+ maxprice, HttpStatus.NOT_FOUND);
        }
    }
    // find all product by brand name and price greater than or equal to the given price
    @GetMapping(value = "/find", params = {"brand","minprice"})
    public ResponseEntity<?> getAllByBrandNameAndPriceGreaterThanEqual(@RequestParam Map<String, String> params){
        String brandName = params.get("brand");
        Double minprice = Double.parseDouble(params.get("minprice"));
        System.err.println(" minprice: "+minprice+" brandName: "+brandName);
        Collection<Product> products = productService.getAllByBrandNameAndPriceGreaterThanEqual(brandName, minprice);
        if(products.size() > 0){
            return new ResponseEntity<>(products, HttpStatus.OK);
        }else{
            return new ResponseEntity<>
                    ("no product found by brand name: "+brandName+" and price greater than or equal to : "+minprice, HttpStatus.NOT_FOUND);
        }
    }



}