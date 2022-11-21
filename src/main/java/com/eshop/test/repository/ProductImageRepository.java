package com.eshop.repository;

import com.eshop.model.ProductSidesImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductSidesImages, Long> {
    ProductSidesImages findByProductId(long productId);
}
