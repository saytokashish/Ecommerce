package com.example.controllers;

import com.example.dto.OrderDTO;
import com.example.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    IOrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        log.info("Received request to get all orders");
        try {
            List<OrderDTO> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            log.error("Failed to fetch orders", e);
            return ResponseEntity.status(500).body("Failed to fetch orders: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long id) {
        log.info("Received request to get order by id: {}", id);
        try {
            OrderDTO order = orderService.getOrderById(id);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error("Failed to fetch order with id: {}", id, e);
            if (e.getMessage() != null && e.getMessage().contains("does not exist")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.status(500).body("Failed to fetch order: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        log.info("Received request to create order: {}", orderDTO);
        try {
            orderService.createOrder(orderDTO);
            return ResponseEntity.status(201).body("Order created successfully");
        } catch (Exception e) {
            log.error("Failed to create order: {}", orderDTO, e);
            return ResponseEntity.status(500).body("Failed to create order: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long id, @RequestBody OrderDTO orderDTO) {
        log.info("Received request to update order with id: {} and data: {}", id, orderDTO);
        try {
            orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok("Order updated successfully");
        } catch (Exception e) {
            log.error("Failed to update order with id: {}", id, e);
            if (e.getMessage() != null && e.getMessage().contains("does not exist")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.status(500).body("Failed to update order: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        log.info("Received request to delete order with id: {}", id);
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (Exception e) {
            log.error("Failed to delete order with id: {}", id, e);
            if (e.getMessage() != null && e.getMessage().contains("does not exist")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.status(500).body("Failed to delete order: " + e.getMessage());
        }
    }
} 