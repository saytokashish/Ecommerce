package com.example.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserClient {
    private final RestTemplate restTemplate = new RestTemplate();
    public boolean userExists(Long userId) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(
                "http://user-service:8081/api/users/" + userId, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
} 