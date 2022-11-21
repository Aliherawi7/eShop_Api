package com.eshop.test.repository;

import com.eshop.test.model.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Long> {
    Collection<FavoriteProduct> findAllByUserEmail(String  userEmail);
    void deleteByProductIdAndUserEmail(Long ProductId, String userEmail);
    FavoriteProduct findByProductIdAndUserEmail(Long productId, String userEmail);
}
