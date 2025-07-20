package com.example.service.impl;

import com.example.dto.ProductDTO;
import com.example.entity.Product;
import com.example.repository.ProductRepository;
import com.example.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService implements IProductService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;

    private ProductDTO toDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
    private Product toEntity(ProductDTO dto) {
        return modelMapper.map(dto, Product.class);
    }

    public List<ProductDTO> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll().stream().map(this::toDTO).toList();
    }
    public ProductDTO getProductById(String id) throws Exception {
        log.info("Fetching product by id: {}", id);
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            log.warn("Product does not exist with id: {}", id);
            throw new Exception("Product does not exist with id: " + id);
        }
        return toDTO(product);
    }
    public void createProduct(ProductDTO productDTO) throws Exception {
        log.info("Creating product: {}", productDTO);
        Product product = toEntity(productDTO);
        product.setId(null);
        product.setQuantity(productDTO.getQuantity());
        productRepository.save(product);
        log.info("Product created successfully: {}", product);
    }
    public void updateProduct(String id, ProductDTO productDTO) throws Exception {
        log.info("Updating product with id: {} and data: {}", id, productDTO);
        Product existing = productRepository.findById(id).orElse(null);
        if (existing == null) {
            log.warn("Product does not exist with id: {}", id);
            throw new Exception("Product does not exist with id: " + id);
        }
        existing.setName(productDTO.getName());
        existing.setDescription(productDTO.getDescription());
        existing.setPrice(productDTO.getPrice());
        existing.setQuantity(productDTO.getQuantity());
        productRepository.save(existing);
        log.info("Product updated successfully: {}", existing);
    }
    public void deleteProduct(String id) throws Exception {
        log.info("Deleting product with id: {}", id);
        if (!productRepository.existsById(id)) {
            log.warn("Product does not exist with id: {}", id);
            throw new Exception("Product does not exist with id: " + id);
        }
        productRepository.deleteById(id);
        log.info("Product deleted successfully with id: {}", id);
    }

    public void decreaseProductQuantity(String id, int amount) throws Exception {
        log.info("Decreasing product quantity for id: {} by amount: {}", id, amount);
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            log.warn("Product does not exist with id: {}", id);
            throw new Exception("Product does not exist with id: " + id);
        }
        if (product.getQuantity() < amount) {
            log.warn("Insufficient product quantity for id: {}. Requested: {}, Available: {}", id, amount, product.getQuantity());
            throw new Exception("Insufficient product quantity.");
        }
        product.setQuantity(product.getQuantity() - amount);
        productRepository.save(product);
        log.info("Product quantity decreased for id: {} by amount: {}", id, amount);
    }

    public void adjustProductQuantity(String id, int oldQty, int newQty) throws Exception {
        log.info("Adjusting product quantity for id: {} from oldQty: {} to newQty: {}", id, oldQty, newQty);
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            log.warn("Product does not exist with id: {}", id);
            throw new Exception("Product does not exist with id: " + id);
        }
        int diff = newQty - oldQty;
        if (product.getQuantity() < diff) {
            log.warn("Insufficient product quantity for update for id: {}. Requested diff: {}, Available: {}", id, diff, product.getQuantity());
            throw new Exception("Insufficient product quantity for update.");
        }
        product.setQuantity(product.getQuantity() - diff);
        productRepository.save(product);
        log.info("Product quantity adjusted for id: {} from oldQty: {} to newQty: {}", id, oldQty, newQty);
    }
} 