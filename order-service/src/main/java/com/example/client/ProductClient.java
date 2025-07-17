package com.example.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductClient {
    private final RestTemplate restTemplate = new RestTemplate();
    public boolean productExists(Long productId) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(
                "http://product-service:8083/api/products/" + productId, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
} 