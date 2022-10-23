package com.eshop.resources;

import com.eshop.model.FavoriteProduct;
import com.eshop.model.Product;
import com.eshop.security.TestUserWithJWT;
import com.eshop.service.FavoriteProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteProductResource {
    private final FavoriteProductService favoriteProductService;
    private final TestUserWithJWT testUserWithJWT;

    public FavoriteProductResource(FavoriteProductService favoriteProductService, TestUserWithJWT testUserWithJWT){
        this.favoriteProductService = favoriteProductService;
        this.testUserWithJWT = testUserWithJWT;
    }

    @GetMapping
    public ResponseEntity<?> getAllFavoriteProducts(HttpServletRequest request){
        String email = testUserWithJWT.getUserEmailByJWT(request);
        if(email != null){
            Collection<Product> products = favoriteProductService.getFavoriteProducts(email);
            if(products.size() > 0){
                return new ResponseEntity<>(products, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(products, HttpStatus.NO_CONTENT);
            }
        }else {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping
    public ResponseEntity<?> addFavoriteProduct(HttpServletRequest request, @RequestBody FavoriteProduct favoriteProduct){
        String email = testUserWithJWT.getUserEmailByJWT(request);
        favoriteProduct.setUserEmail(email);
        favoriteProductService.addFavoriteProduct(favoriteProduct);
        return ResponseEntity.ok().body("product successfully added");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteFavoriteProduct(@PathVariable Long productId, HttpServletRequest request){
        String email = testUserWithJWT.getUserEmailByJWT(request);
        System.out.println(productId);
        System.out.println(email);
        favoriteProductService.deleteFavoriteProduct(productId, email);
        System.out.println(productId);
        return new ResponseEntity<>("successfully deleted", HttpStatus.OK);
    }
}
