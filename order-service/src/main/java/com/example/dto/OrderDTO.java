package com.example.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private int quantity;
    private String status;
} 