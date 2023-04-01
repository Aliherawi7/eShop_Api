package com.eshop.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileStorageLocation {

    private String userProfileUploadDir;
    private String productUploadDir;
    private String brandUploadDir;

    public FileStorageLocation(
            @Value("${file.user.profile.upload.dir}") String userProfileUploadDir,
            @Value("${file.product.upload.dir}") String productUploadDir,
            @Value("${file.brand.upload.dir}") String brandUploadDir) {
        this.userProfileUploadDir = userProfileUploadDir;
        this.productUploadDir = productUploadDir;
        this.brandUploadDir = brandUploadDir;
    }

    public String getUserProfileUploadDir() {
        return userProfileUploadDir;
    }

    public void setUserProfileUploadDir(String userProfileUploadDir) {
        this.userProfileUploadDir = userProfileUploadDir;
    }

    public String getProductUploadDir() {
        return productUploadDir;
    }

    public void setProductUploadDir(String productUploadDir) {
        this.productUploadDir = productUploadDir;
    }

    public String getBrandUploadDir() {
        return brandUploadDir;
    }

    public void setBrandUploadDir(String brandUploadDir) {
        this.brandUploadDir = brandUploadDir;
    }
}
