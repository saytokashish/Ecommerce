package com.example.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public boolean userExists(Long userId) {
        log.info("Checking if user exists with id: {}", userId);
        try {
            log.info("Calling user service for checking if user exists");
            ResponseEntity<String> response = restTemplate.getForEntity(
                    userServiceUrl + "/api/users/" + userId, String.class);
            boolean exists = response.getStatusCode().is2xxSuccessful();
            log.info("User exists check for id {}: {}", userId, exists);
            return exists;
        } catch (Exception e) {
            log.error("Exception occurred while checking user exists for id: {}", userId, e);
            return false;
        }
    }

} 