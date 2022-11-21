package com.eshop.service;

import com.eshop.dto.ProductDTO;
import com.eshop.model.FavoriteProduct;
import com.eshop.model.Product;
import com.eshop.model.ProductSidesImages;
import com.eshop.repository.FavoriteProductRepository;
import com.eshop.repository.ProductImageRepository;
import com.eshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class FavoriteProductService {
     private final FavoriteProductRepository favoriteProductRepository;
     private final ProductRepository productRepository;
     private final ProductImageRepository productImageRepository;

    public FavoriteProductService(FavoriteProductRepository favoriteProductRepository, ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.favoriteProductRepository = favoriteProductRepository;
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }

    public Collection<ProductDTO> getFavoriteProducts(String email){
        System.out.println(email);
       Collection<FavoriteProduct> favoriteProducts = favoriteProductRepository.findAllByUserEmail(email);
        System.out.println(favoriteProducts);
       Collection<ProductDTO> products = new ArrayList<>();

       // dou to Null pointer exception
       if(favoriteProducts.size() > 0){
           favoriteProducts.forEach(favoriteProduct -> {
              Product product = productRepository.findById(favoriteProduct.getProductId()).get();
              ProductDTO productDTO = new ProductDTO();
              productDTO.setProductId(product.getId());
              productDTO.setPrice(product.getPrice());
              productDTO.setCategory(product.getCategory());
              productDTO.setDiscount(product.getDiscount());
              productDTO.setName(product.getName());
              productDTO.setRate(product.getRate());
              ProductSidesImages productSidesImages =
                       productImageRepository.findByProductId(product.getId());
              ArrayList<byte[]> image = new ArrayList<>();
              image.add(productSidesImages.getSide1());
              productDTO.setImages(image);
              products.add(productDTO);
           });
           return products;
       }else return products;
    }

    public boolean addFavoriteProduct(FavoriteProduct favoriteProduct){
        if(favoriteProduct.getProductId() !=null){
            FavoriteProduct product = favoriteProductRepository
                    .findByProductIdAndUserEmail(favoriteProduct.getProductId(), favoriteProduct.getUserEmail());
            if(product == null){
                favoriteProductRepository.save(favoriteProduct);
                return true;
            }
        }
        return false;
    }

    public void deleteFavoriteProduct(Long productId, String userEmail){
        System.out.println(productId);
        favoriteProductRepository.delete(favoriteProductRepository.findByProductIdAndUserEmail(productId, userEmail));

    }
}
