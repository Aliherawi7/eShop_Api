package com.eshop.service;

import com.eshop.constants.APIEndpoints;
import com.eshop.dto.ProductDTO;
import com.eshop.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProductDTOMapper implements Function<Product, ProductDTO> {

    @Override
    public ProductDTO apply(Product product) {
        ArrayList<String> images = new ArrayList<>();
        images.add(APIEndpoints.PRODUCT_IMAGES.getValue()+product.getId()+"/side-1");
        images.add(APIEndpoints.PRODUCT_IMAGES.getValue()+product.getId()+"/side-2");
        images.add(APIEndpoints.PRODUCT_IMAGES.getValue()+product.getId()+"/side-3");
        String[] str = product.getDescription().split("\n");
        ArrayList<String> descriptions = (ArrayList<String>) Stream.of(str).collect(Collectors.toList());
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getColor(),
                images,
                product.getBrandName(),
                product.getCategory(),
                product.getPrice(),
                descriptions,
                product.getProductionDate(),
                product.getSize(),
                product.getQuantityInDepot(),
                product.getRate(),
                product.getDiscount(),
                0
        );
    }
}
