package com.eshop.service;


import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class RequestService {

    private final String LOCALHOST_IPV4 = "127.0.0.1";
    private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    public String getClientIP(HttpServletRequest request){
        String ipAddress = request.getHeader("X-Forwarded-For");

        if(ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getRemoteAddr();
            if(LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)){
                try{
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ipAddress = inetAddress.getHostAddress();
                }catch (UnknownHostException e){
                    e.printStackTrace();
                }
            }
        }
        return ipAddress;
    }
}
