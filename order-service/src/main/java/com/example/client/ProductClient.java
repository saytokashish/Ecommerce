package com.example.client;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ProductClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public boolean productExists(String productId) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(
                productServiceUrl + "/api/products/" + productId, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
    public ProductDTO getProductById(String productId) {
        try {
            ResponseEntity<ProductClient.ProductDTO> response = restTemplate.getForEntity(
                productServiceUrl + "/api/products/" + productId, ProductDTO.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (Exception e) {
            // handle exception or return null
        }
        return null;
    }

    public void decreaseProductQuantity(String productId, int amount) {
        String url = productServiceUrl + "/api/products/" + productId + "/decrease-quantity?amount=" + amount;
        restTemplate.patchForObject(url, null, String.class);
    }

    public void adjustProductQuantity(String productId, int oldQty, int newQty) {
        String url = productServiceUrl + "/api/products/" + productId + "/adjust-quantity?oldQty=" + oldQty + "&newQty=" + newQty;
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