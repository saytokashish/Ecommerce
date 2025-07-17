package com.example.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class UserDTO {
    Long id;
    @NotBlank(message = "Name is required")
    String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email;
    String phoneNumber;
    String address;
}
