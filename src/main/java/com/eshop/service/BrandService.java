package com.eshop.service;

import com.eshop.constants.APIEndpoints;
import com.eshop.model.Brand;
import com.eshop.repository.BrandRepository;
import com.eshop.utils.BaseURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    @Autowired
    HttpServletRequest httpServletRequest;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    public Collection<Brand> getAllBrands() {
        String baseURI = BaseURI.getBaseURI(httpServletRequest);
        return brandRepository
                .findAll()
                .stream()
                .peek(item -> item.setLogo(baseURI + APIEndpoints.BRAND_IMAGES.getValue() + item.getId()))
                .collect(Collectors.toList());
    }

    public Brand getBrand(int id) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        return optionalBrand.orElse(null);
    }

    public Brand getBrandByName(String name) {
        String baseURI = BaseURI.getBaseURI(httpServletRequest);
        Optional<Brand> optionalBrand = brandRepository.findByName(name);
        optionalBrand.ifPresent(brand -> brand.setLogo(baseURI + APIEndpoints.BRAND_IMAGES.getValue() + brand.getId()));
        return optionalBrand.orElse(null);
    }

    public void addBrand(Brand brand) {
        brandRepository.save(brand);
    }

    public void updateBrand(Brand brand) {
        brandRepository.save(brand);
    }
}
