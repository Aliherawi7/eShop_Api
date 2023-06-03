package com.eshop.utils;


import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;


public class BaseURI {
    public static String getBaseURI(HttpServletRequest httpServletRequest) {
        return ServletUriComponentsBuilder.fromRequestUri(httpServletRequest)
                .replacePath(null)
                .build().toUriString()+"/";
    }
}
