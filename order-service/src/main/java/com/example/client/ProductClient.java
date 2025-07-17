package com.example.client;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductClient {
    private final RestTemplate restTemplate = new RestTemplate();
    public boolean productExists(String productId) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(
                "http://product-service:8083/api/products/" + productId, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
    public ProductDTO getProductById(String productId) {
        try {
            ResponseEntity<ProductClient.ProductDTO> response = restTemplate.getForEntity(
                "http://product-service:8083/api/products/" + productId, ProductDTO.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (Exception e) {
            // handle exception or return null
        }
        return null;
    }

    public void decreaseProductQuantity(String productId, int amount) {
        String url = "http://product-service:8083/api/products/" + productId + "/decrease-quantity?amount=" + amount;
        restTemplate.patchForObject(url, null, String.class);
    }

    public void adjustProductQuantity(String productId, int oldQty, int newQty) {
        String url = "http://product-service:8083/api/products/" + productId + "/adjust-quantity?oldQty=" + oldQty + "&newQty=" + newQty;
        restTemplate.patchForObject(url, null, String.class);
    }
    @Data
    public static class ProductDTO {
        private String id;
        private String name;
        private String description;
        private double price;
        private int quantity;
    }
} 