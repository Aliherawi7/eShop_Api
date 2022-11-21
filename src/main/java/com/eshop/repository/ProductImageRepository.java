package com.eshop.test.repository;

import com.eshop.test.model.ProductSidesImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductSidesImages, Long> {
    ProductSidesImages findByProductId(long productId);
}
