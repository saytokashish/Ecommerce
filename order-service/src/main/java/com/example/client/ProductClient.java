package com.example.client;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Service
public class ProductClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public boolean productExists(String productId) {
        log.info("Checking if product exists with id: {}", productId);
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(
                productServiceUrl + "/api/products/" + productId, String.class);
            boolean exists = response.getStatusCode().is2xxSuccessful();
            log.info("Product exists check for id {}: {}", productId, exists);
            return exists;
        } catch (Exception e) {
            log.error("Exception occurred while checking if product exists for id: {}", productId, e);
            return false;
        }
    }
    public ProductDTO getProductById(String productId) {
        log.info("Fetching product by id: {}", productId);
        try {
            ResponseEntity<ProductClient.ProductDTO> response = restTemplate.getForEntity(
                productServiceUrl + "/api/products/" + productId, ProductDTO.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Fetched product details for id: {}", productId);
                return response.getBody();
            }
        } catch (Exception e) {
            log.error("Exception occurred while fetching product by id: {}", productId, e);
        }
        return null;
    }

    public void decreaseProductQuantity(String productId, int amount) {
        log.info("Decreasing product quantity for id: {} by amount: {}", productId, amount);
        String url = productServiceUrl + "/api/products/" + productId + "/decrease-quantity?amount=" + amount;
        try {
            restTemplate.patchForObject(url, null, String.class);
            log.info("Decreased product quantity for id: {} by amount: {}", productId, amount);
        } catch (Exception e) {
            log.error("Exception occurred while decreasing product quantity for id: {}", productId, e);
            throw e;
        }
    }

    public void adjustProductQuantity(String productId, int oldQty, int newQty) {
        log.info("Adjusting product quantity for id: {} from oldQty: {} to newQty: {}", productId, oldQty, newQty);
        String url = productServiceUrl + "/api/products/" + productId + "/adjust-quantity?oldQty=" + oldQty + "&newQty=" + newQty;
        try {
            restTemplate.patchForObject(url, null, String.class);
            log.info("Adjusted product quantity for id: {} from oldQty: {} to newQty: {}", productId, oldQty, newQty);
        } catch (Exception e) {
            log.error("Exception occurred while adjusting product quantity for id: {}", productId, e);
            throw e;
        }
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