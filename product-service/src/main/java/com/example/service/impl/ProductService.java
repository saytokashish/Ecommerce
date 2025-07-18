package com.example.service.impl;

import com.example.dto.ProductDTO;
import com.example.entity.Product;
import com.example.repository.ProductRepository;
import com.example.service.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return productRepository.findAll().stream().map(this::toDTO).toList();
    }
    public ProductDTO getProductById(String id) throws Exception {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) throw new Exception("Product does not exist with id: " + id);
        return toDTO(product);
    }
    public void createProduct(ProductDTO productDTO) throws Exception {
        Product product = toEntity(productDTO);
        product.setId(null);
        product.setQuantity(productDTO.getQuantity());
        productRepository.save(product);
    }
    public void updateProduct(String id, ProductDTO productDTO) throws Exception {
        Product existing = productRepository.findById(id).orElse(null);
        if (existing == null) throw new Exception("Product does not exist with id: " + id);
        existing.setName(productDTO.getName());
        existing.setDescription(productDTO.getDescription());
        existing.setPrice(productDTO.getPrice());
        existing.setQuantity(productDTO.getQuantity());
        productRepository.save(existing);
    }
    public void deleteProduct(String id) throws Exception {
        if (!productRepository.existsById(id)) throw new Exception("Product does not exist with id: " + id);
        productRepository.deleteById(id);
    }

    public void decreaseProductQuantity(String id, int amount) throws Exception {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) throw new Exception("Product does not exist with id: " + id);
        if (product.getQuantity() < amount) throw new Exception("Insufficient product quantity.");
        product.setQuantity(product.getQuantity() - amount);
        productRepository.save(product);
    }

    public void adjustProductQuantity(String id, int oldQty, int newQty) throws Exception {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) throw new Exception("Product does not exist with id: " + id);
        int diff = newQty - oldQty;
        if (product.getQuantity() < diff) throw new Exception("Insufficient product quantity for update.");
        product.setQuantity(product.getQuantity() - diff);
        productRepository.save(product);
    }
} 