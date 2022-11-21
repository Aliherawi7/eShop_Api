package com.eshop.test.service;

import com.eshop.test.model.Brand;
import com.eshop.test.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository){
        this.brandRepository = brandRepository;
    }


    public Collection<Brand> getAllBrands(){
        return brandRepository.findAll();
    }

    public Brand getBrand(int id){
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        return optionalBrand.orElse(null);
    }

    public Brand getBrandByName(String name){
        Optional<Brand> optionalBrand = brandRepository.findByName(name);
        return optionalBrand.orElse(null);
    }
    public void addBrand(Brand brand){
        brandRepository.save(brand);
    }
    public void updateBrand(Brand brand){
        brandRepository.save(brand);
    }
}
