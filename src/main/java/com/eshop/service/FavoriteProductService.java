package com.eshop.service;

import com.eshop.dto.ProductDTO;
import com.eshop.model.FavoriteProduct;
import com.eshop.model.Product;
import com.eshop.repository.FavoriteProductRepository;
import com.eshop.repository.ProductRepository;
import com.eshop.utils.BaseURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class FavoriteProductService {
    private final FavoriteProductRepository favoriteProductRepository;
    private final ProductRepository productRepository;
    private final ProductDTOMapper productDTOMapper;
    @Autowired
    HttpServletRequest httpServletRequest;

    public FavoriteProductService(FavoriteProductRepository favoriteProductRepository,
                                  ProductRepository productRepository,
                                  ProductDTOMapper productDTOMapper) {
        this.favoriteProductRepository = favoriteProductRepository;
        this.productRepository = productRepository;
        this.productDTOMapper = productDTOMapper;
    }

    public Collection<ProductDTO> getFavoriteProducts(String email) {
        Collection<FavoriteProduct> favoriteProducts = favoriteProductRepository.findAllByUserEmail(email);
        Collection<ProductDTO> products = new ArrayList<>();
        String baseURI = BaseURI.getBaseURI(httpServletRequest);

        // dou to Null pointer exception
        if (favoriteProducts.size() > 0) {
            favoriteProducts.forEach(favoriteProduct -> {
                Product product = productRepository.findById(favoriteProduct.getProductId()).get();
                ProductDTO productDTO = productDTOMapper.apply(product);
                ArrayList<String> images = new ArrayList<>();
                productDTO.getImages().forEach(item -> images.add(baseURI + item));
                productDTO.setImages(images);
                products.add(productDTO);
            });
        }
        return products;
    }

    public boolean addFavoriteProduct(FavoriteProduct favoriteProduct) {
        if (favoriteProduct.getProductId() != null) {
            FavoriteProduct product = favoriteProductRepository
                    .findByProductIdAndUserEmail(favoriteProduct.getProductId(), favoriteProduct.getUserEmail());
            if (product == null) {
                favoriteProductRepository.save(favoriteProduct);
                return true;
            }
        }
        return false;
    }

    public void deleteFavoriteProduct(Long productId, String userEmail) {
        System.out.println(productId);
        favoriteProductRepository.delete(favoriteProductRepository.findByProductIdAndUserEmail(productId, userEmail));

    }
}
