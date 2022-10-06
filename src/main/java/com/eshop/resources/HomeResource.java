package com.eshop.resources;

import com.eshop.service.IPFinderService;
import com.eshop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeResource {

    @Autowired
    private IPFinderService ipFinderService;
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ResponseEntity<String> index(HttpServletRequest request) {
        String clientIP = ipFinderService.getClientIP(request);
        ObjectMapper response = new ObjectMapper();
        Map<String, String> responseValues = new HashMap<>();
        responseValues.put("IP Address", clientIP);
        responseValues.put("welcomeMessage", "Welcome to eshop store");

        return new ResponseEntity(responseValues, HttpStatus.OK);
    }

}
