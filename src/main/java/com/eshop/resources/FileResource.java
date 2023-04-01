package com.eshop.resources;


import com.eshop.service.FileStorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/files")
public class FileResource {

    private final FileStorageService fileStorageService;

    public FileResource(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("user-profile/{username}")
    public ResponseEntity<byte[]> getUserProfilePicture(@PathVariable String username) {
        byte[] image = fileStorageService.getUserImage(username);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
    }

    @GetMapping("product-image/{productId}/{side}")
    public ResponseEntity<byte[]> getProductImageSide(@PathVariable String productId, @PathVariable String side) {
        productId = productId +"-"+ side;
        byte[] image = fileStorageService.getProductImage(productId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
    }

    @GetMapping("brand-image/{brandId}")
    public ResponseEntity<byte[]> getBrandImages(@PathVariable String brandId) {
        byte[] image = fileStorageService.getBrandImage(brandId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
    }

}
