package com.eshop.service;

import com.eshop.model.FavoriteProduct;
import com.eshop.model.Product;
import com.eshop.repository.FavoriteProductRepository;
import com.eshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class FavoriteProductSevice {
     private final FavoriteProductRepository favoriteProductRepository;
     private final ProductRepository productRepository;

    public FavoriteProductSevice(FavoriteProductRepository favoriteProductRepository, ProductRepository productRepository){
        this.favoriteProductRepository = favoriteProductRepository;
        this.productRepository = productRepository;
    }

    public Collection<Product> getFavoriteProducts(String email ){
       Collection<FavoriteProduct> favoriteProducts = favoriteProductRepository.findAllByUserEmail(email);
       Collection<Product> products = new ArrayList<>();

       // dou to Null pointer exception
       if(favoriteProducts.size() > 0){
           favoriteProducts.forEach(favoriteProduct -> products.add(productRepository.findById(favoriteProduct.getProductId()).get()));
           return products;
       }else return products;
    }

    public void addFavoriteProduct(FavoriteProduct favoriteProduct){
        FavoriteProduct product = favoriteProductRepository
                .findByProductIdAndUserEmail(favoriteProduct.getProductId(), favoriteProduct.getUserEmail());
        if(product == null){
            favoriteProductRepository.save(favoriteProduct);
        }

    }

    public void deleteFavoriteProduct(Long productId, String userEmail){
        System.out.println(productId);
        favoriteProductRepository.delete(favoriteProductRepository.findByProductIdAndUserEmail(productId, userEmail));

    }
}
