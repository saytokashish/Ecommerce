package com.example.service;

import com.example.dto.ProductDTO;
import java.util.List;

public interface IProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(String id) throws Exception;
    void createProduct(ProductDTO productDTO) throws Exception;
    void updateProduct(String id, ProductDTO productDTO) throws Exception;
    void deleteProduct(String id) throws Exception;
    void decreaseProductQuantity(String id, int amount) throws Exception;
    void adjustProductQuantity(String id, int oldQty, int newQty) throws Exception;
} 