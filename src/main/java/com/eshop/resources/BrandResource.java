package com.eshop.resources;

import com.eshop.model.Brand;
import com.eshop.service.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/brands")
public class BrandResource {

    private final BrandService brandService;

    public BrandResource(BrandService brandService){
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBrand(){
        Collection<Brand> brands = brandService.getAllBrands();
        if(brands.size() > 0){
            return new ResponseEntity<>(brands, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(brands, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBrand(@PathVariable int id){
        Brand brand = brandService.getBrand(id);
        if(brand != null){
            return new ResponseEntity<>(brand, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no content", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getBrand(@PathVariable String name){
        Brand brand = brandService.getBrandByName(name);
        if(brand != null){
            return new ResponseEntity<>(brand, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no content", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<?> addBrand(@RequestParam("image") MultipartFile file, @RequestParam Map<String, String> params) throws IOException {
        Brand brand = new Brand();
        brand.setLogo(brand.getId()+"");
        brand.setName(params.get("name"));
        brandService.addBrand(brand);
        return new ResponseEntity<>(brand, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable int id, @RequestParam("image") MultipartFile file, @RequestParam Map<String, String> params) throws IOException {
        Brand brand = brandService.getBrand(id);
        if(brand == null){
            return new ResponseEntity<>("brand not found with id "+ id, HttpStatus.NO_CONTENT);
        }
        brand.setName(params.get("name"));
        brand.setLogo(id+"");
        brandService.updateBrand(brand);
        return new ResponseEntity<>(brand, HttpStatus.CREATED);
    }

}
