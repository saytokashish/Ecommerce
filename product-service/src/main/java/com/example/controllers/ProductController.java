package com.example.controllers;

import com.example.dto.ProductDTO;
import com.example.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    IProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        log.info("Received request to get all products");
        try {
            List<ProductDTO> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            log.error("Failed to fetch products", e);
            return ResponseEntity.status(500).body("Failed to fetch products: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") String id) {
        log.info("Received request to get product by id: {}", id);
        try {
            ProductDTO product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            log.error("Failed to fetch product with id: {}", id, e);
            if (e.getMessage() != null && e.getMessage().contains("does not exist")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.status(500).body("Failed to fetch product: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        log.info("Received request to create product: {}", productDTO);
        try {
            productService.createProduct(productDTO);
            return ResponseEntity.status(201).body("Product created successfully");
        } catch (Exception e) {
            log.error("Failed to create product: {}", productDTO, e);
            return ResponseEntity.status(500).body("Failed to create product: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") String id, @RequestBody ProductDTO productDTO) {
        log.info("Received request to update product with id: {} and data: {}", id, productDTO);
        try {
            productService.updateProduct(id, productDTO);
            return ResponseEntity.ok("Product updated successfully");
        } catch (Exception e) {
            log.error("Failed to update product with id: {}", id, e);
            if (e.getMessage() != null && e.getMessage().contains("does not exist")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.status(500).body("Failed to update product: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
        log.info("Received request to delete product with id: {}", id);
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            log.error("Failed to delete product with id: {}", id, e);
            if (e.getMessage() != null && e.getMessage().contains("does not exist")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.status(500).body("Failed to delete product: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/decrease-quantity")
    public ResponseEntity<?> decreaseProductQuantity(@PathVariable("id") String id, @RequestParam(name = "amount") int amount) {
        log.info("Received request to decrease product quantity for id: {} by amount: {}", id, amount);
        try {
            productService.decreaseProductQuantity(id, amount);
            return ResponseEntity.ok("Product quantity decreased successfully");
        } catch (Exception e) {
            log.error("Failed to decrease product quantity for id: {} by amount: {}", id, amount, e);
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/adjust-quantity")
    public ResponseEntity<?> adjustProductQuantity(@PathVariable("id") String id, @RequestParam(name = "oldQty") int oldQty, @RequestParam(name = "newQty") int newQty) {
        log.info("Received request to adjust product quantity for id: {} from oldQty: {} to newQty: {}", id, oldQty, newQty);
        try {
            productService.adjustProductQuantity(id, oldQty, newQty);
            return ResponseEntity.ok("Product quantity adjusted successfully");
        } catch (Exception e) {
            log.error("Failed to adjust product quantity for id: {} from oldQty: {} to newQty: {}", id, oldQty, newQty, e);
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
} 