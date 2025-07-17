package com.example.controllers;

import com.example.dto.UserDTO;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    IUserService userService;

     @GetMapping
     public ResponseEntity<?> getAllUsers() {
         try {
             List<UserDTO> users = userService.getAllUsers();
             return ResponseEntity.ok(users);
         } catch (Exception e) {
             return ResponseEntity.status(500).body("Failed to fetch users: " + e.getMessage());
         }
     }

     @GetMapping("/{id}")
     public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
         try {
             UserDTO user = userService.getUserById(id);
             return ResponseEntity.ok(user);
         } catch (Exception e) {
             if (e.getMessage() != null && e.getMessage().contains("does not exist")) {
                 return ResponseEntity.status(404).body(e.getMessage());
             }
             return ResponseEntity.status(500).body("Failed to fetch user: " + e.getMessage());
         }
     }

     @PostMapping
     public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {
         try {
             userService.createUser(userDTO);
             return ResponseEntity.status(201).body("User created successfully");
         } catch (Exception e) {
             return ResponseEntity.status(500).body("Failed to create user: " + e.getMessage());
         }
     }

     @DeleteMapping("/{id}")
     public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
         try {
             userService.deleteUser(id);
             return ResponseEntity.ok("User deleted successfully");
         } catch (Exception e) {
             if (e.getMessage() != null && e.getMessage().contains("does not exist")) {
                 return ResponseEntity.status(404).body(e.getMessage());
             }
             return ResponseEntity.status(500).body("Failed to delete user: " + e.getMessage());
         }
     }

     @PutMapping("/{id}")
     public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserDTO userDTO) {
         try {
             userService.updateUser(id, userDTO);
             return ResponseEntity.ok("User updated successfully");
         } catch (Exception e) {
             if (e.getMessage() != null && e.getMessage().contains("does not exist")) {
                 return ResponseEntity.status(404).body(e.getMessage());
             }
             return ResponseEntity.status(500).body("Failed to update user: " + e.getMessage());
         }
     }
}
