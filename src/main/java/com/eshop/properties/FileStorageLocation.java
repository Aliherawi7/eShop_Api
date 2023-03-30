package com.eshop.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileStorageLocation {

    private String userProfileUploadDir;
    private String productUploadDir;

    public FileStorageLocation(
            @Value("${file.user.profile.upload.dir}") String userProfileUploadDir,
            @Value("${file.product.upload.dir}") String productUploadDir) {
        this.userProfileUploadDir = userProfileUploadDir;
        this.productUploadDir = productUploadDir;
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
}
