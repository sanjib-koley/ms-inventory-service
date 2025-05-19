package com.sanjib.edureka.ms_inventory_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TokenService
{
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);
    
    @Autowired
    ApplicationContext ctx;

    public boolean validateToken(String token)
    {
        // Forward request to Auth-Service for Token Validation
        // If token is valid, update user PII in the database
        log.info("forwarding request to auth-service for token validation");
        WebClient authValidateWebClient = ctx.getBean("authValidateWebClientEurekaDiscovered", WebClient.class);

        String authResponse =authValidateWebClient.get()
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Thread is Blocked until the response is received

        log.info("Response from auth-service: {}", authResponse);

        if(authResponse.equals("INVALID"))
        {
            log.info("Invalid token: {}", token);
            return false;
        }
        else if (authResponse.equals("VALID"))
        {
            log.info("Valid token: {}", token);
            // Return success or failure response
            return true;
        }
        else
        {
            log.info("Error in auth-service: {}", authResponse);
            return false;
        }
    }
    
    public Long getUserIdFromToken(String token)
    {
    	
        WebClient authValidateWebClient = ctx.getBean("authUseIdFromTokenWebClientEureka", WebClient.class);

        Long userId =authValidateWebClient.get()
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Long.class)
                .block(); // Thread is Blocked until the response is received

        log.info("UserId from auth-service: {}", userId);

        if(userId == 0L) {
        	throw new RuntimeException("UserId cannot be fetched from token");
        }
        return  userId;
    }
    
    public String getProductNameFromId(String token,String usertype,Integer productId)
    {
    	
        WebClient productFetchNameWebClient = ctx.getBean("productFetchNameWebClientEureka", WebClient.class);

        String productName = null;
        productName =productFetchNameWebClient.get()
        		.uri("/{id}", productId)
                .header("Authorization", token)
                .header("Usertype", usertype)
                
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Thread is Blocked until the response is received

        log.info("Product Name from product-service: {}", productName);

        if(productName == null) {
        	throw new RuntimeException("Product Name cannot be fetched from Id:::"+productId);
        }
        return  productName;
    }
    
    public Integer addProductQuantity(String token,String usertype,Integer productId,Integer quantity)
    {
    	
        WebClient productFetchNameWebClient = ctx.getBean("addProductQuantityEureka", WebClient.class);

        Integer quantityNew = null;
        quantityNew =productFetchNameWebClient.put()
        		.uri("/{id}/{quantity}", productId,quantity)
                .header("Authorization", token)
                .header("Usertype", usertype)
                .retrieve()
                .bodyToMono(Integer.class)
                .block(); // Thread is Blocked until the response is received

        log.info("Quantity  from product-service: {}", quantityNew);

        if(quantityNew == null) {
        	throw new RuntimeException("Product Qauntity not updated for product Id:::"+productId);
        }
        return  quantityNew;
    }



}
