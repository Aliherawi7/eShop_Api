package com.eshop.resources;

import com.eshop.service.IPFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HomeResource {

    @Autowired
    private IPFinderService ipFinderService;

    @GetMapping("/")
    public ResponseEntity<String> index(HttpServletRequest request){

        String clientIP = ipFinderService.getClientIP(request);
        return ResponseEntity.ok(clientIP);
    }

}
