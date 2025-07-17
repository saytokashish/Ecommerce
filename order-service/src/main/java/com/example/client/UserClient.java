package com.example.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserClient {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${user.service.url}")
    private String userServiceUrl;
    public boolean userExists(Long userId) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(
                userServiceUrl + "/api/users/" + userId, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
} 