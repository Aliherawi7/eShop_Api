package com.eshop.service;

import com.eshop.dto.SummeryDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StatisticsService {
    private final OrderService orderService;
    private final  ProductService productService;
    private final UserService userService;
    private final BrandService brandService;

    public StatisticsService(ProductService productService, OrderService orderService,
                             UserService userService, BrandService brandService){
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
        this.brandService = brandService;
    }

    public SummeryDTO getModelSummery(){
        int products = productService.getAllProducts().size();
        int orders = orderService.getAllOrderSize();
        int users = userService.getAllUserSize();
        int categories = 5;
        int brands = brandService.getAllBrandSize();
        return new SummeryDTO(products, orders, categories, users, brands);
    }
}
