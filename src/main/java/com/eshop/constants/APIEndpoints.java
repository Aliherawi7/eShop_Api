package com.eshop.constants;

public enum APIEndpoints {
    USER_PICTURE("api/v1/files/user-profile/"),
    PRODUCT_IMAGES("api/v1/files/product-image/");

    final String value;
    APIEndpoints(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
