package com.eshop.test.resources;

import com.eshop.test.dto.ProductDTO;
import com.eshop.test.model.FavoriteProduct;
import com.eshop.test.security.TestUserWithJWT;
import com.eshop.test.service.FavoriteProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteProductResource {
    private final FavoriteProductService favoriteProductService;

    public FavoriteProductResource(FavoriteProductService favoriteProductService){
        this.favoriteProductService = favoriteProductService;
    }

    @GetMapping
    public ResponseEntity<?> getAllFavoriteProducts(HttpServletRequest request){
        String email = TestUserWithJWT.getUserEmailByJWT(request);
        System.out.println("email in get fav products : "+email );
        if(email != null){
            Collection<ProductDTO> products = favoriteProductService.getFavoriteProducts(email);
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
        String email = TestUserWithJWT.getUserEmailByJWT(request);
        favoriteProduct.setUserEmail(email);
        boolean isSave = favoriteProductService.addFavoriteProduct(favoriteProduct);
        if(isSave){
            return ResponseEntity.ok().body("product successfully added");
        }else {
            return ResponseEntity.badRequest().body("product with this id not found");
        }

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteFavoriteProduct(@PathVariable Long productId, HttpServletRequest request){
        String email = TestUserWithJWT.getUserEmailByJWT(request);
        System.out.println(productId);
        System.out.println(email);
        favoriteProductService.deleteFavoriteProduct(productId, email);
        System.out.println(productId);
        return new ResponseEntity<>("successfully deleted", HttpStatus.OK);
    }
}
