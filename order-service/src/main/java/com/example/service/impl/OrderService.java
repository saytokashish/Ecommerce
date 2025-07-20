package com.example.service.impl;

import com.example.dto.OrderDTO;
import com.example.entity.Order;
import com.example.repository.OrderRepository;
import com.example.service.IOrderService;
import com.example.client.UserClient;
import com.example.client.ProductClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
public class OrderService implements IOrderService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserClient userClient;
    @Autowired
    private ProductClient productClient;

    private OrderDTO toDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
    private Order toEntity(OrderDTO dto) {
        return modelMapper.map(dto, Order.class);
    }

    public List<OrderDTO> getAllOrders() {
        log.info("Fetching all orders");
        return orderRepository.findAll().stream().map(this::toDTO).toList();
    }
    public OrderDTO getOrderById(Long id) throws Exception {
        log.info("Fetching order by id: {}", id);
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            log.warn("Order does not exist with id: {}", id);
            throw new Exception("Order does not exist with id: " + id);
        }
        return toDTO(order);
    }
    public void createOrder(OrderDTO orderDTO) throws Exception {
        log.info("Creating order: {}", orderDTO);
        if (!userClient.userExists(orderDTO.getUserId())) {
            log.warn("User does not exist with id: {}", orderDTO.getUserId());
            throw new Exception("User does not exist with id: " + orderDTO.getUserId());
        }
        if (!productClient.productExists(orderDTO.getProductId())) {
            log.warn("Product does not exist with id: {}", orderDTO.getProductId());
            throw new Exception("Product does not exist with id: " + orderDTO.getProductId());
        }
        ProductClient.ProductDTO product = productClient.getProductById(orderDTO.getProductId());
        if (product == null) {
            log.error("Failed to fetch product details for id: {}", orderDTO.getProductId());
            throw new Exception("Failed to fetch product details for id: " + orderDTO.getProductId());
        }
        if (orderDTO.getQuantity() > product.getQuantity()) {
            log.warn("Requested quantity {} exceeds available product quantity {} for product id: {}", orderDTO.getQuantity(), product.getQuantity(), orderDTO.getProductId());
            throw new Exception("Requested quantity exceeds available product quantity.");
        }
        Order order = toEntity(orderDTO);
        order.setId(null);
        orderRepository.save(order);
        try {
            productClient.decreaseProductQuantity(orderDTO.getProductId(), orderDTO.getQuantity());
        } catch (Exception e) {
            log.error("Failed to decrease product quantity for product id: {}, rolling back order creation", orderDTO.getProductId(), e);
            orderRepository.delete(order);
            throw new Exception("Failed to decrease product quantity, order creation rolled back.", e);
        }
        log.info("Order created successfully: {}", order);
    }
    public void updateOrder(Long id, OrderDTO orderDTO) throws Exception {
        log.info("Updating order with id: {} and data: {}", id, orderDTO);
        Order existing = orderRepository.findById(id).orElse(null);
        if (existing == null) {
            log.warn("Order does not exist with id: {}", id);
            throw new Exception("Order does not exist with id: " + id);
        }
        if (!userClient.userExists(orderDTO.getUserId())) {
            log.warn("User does not exist with id: {}", orderDTO.getUserId());
            throw new Exception("User does not exist with id: " + orderDTO.getUserId());
        }
        if (!productClient.productExists(orderDTO.getProductId())) {
            log.warn("Product does not exist with id: {}", orderDTO.getProductId());
            throw new Exception("Product does not exist with id: " + orderDTO.getProductId());
        }
        ProductClient.ProductDTO product = productClient.getProductById(orderDTO.getProductId());
        if (product == null) {
            log.error("Failed to fetch product details for id: {}", orderDTO.getProductId());
            throw new Exception("Failed to fetch product details for id: " + orderDTO.getProductId());
        }
        if (orderDTO.getQuantity() > product.getQuantity()) {
            log.warn("Requested quantity {} exceeds available product quantity {} for product id: {}", orderDTO.getQuantity(), product.getQuantity(), orderDTO.getProductId());
            throw new Exception("Requested quantity exceeds available product quantity.");
        }
        // Save a copy of the old state for rollback
        Order oldOrder = new Order();
        oldOrder.setId(existing.getId());
        oldOrder.setUserId(existing.getUserId());
        oldOrder.setProductId(existing.getProductId());
        oldOrder.setQuantity(existing.getQuantity());
        oldOrder.setStatus(existing.getStatus());
        existing.setUserId(orderDTO.getUserId());
        existing.setProductId(orderDTO.getProductId());
        existing.setQuantity(orderDTO.getQuantity());
        existing.setStatus(orderDTO.getStatus());
        orderRepository.save(existing);
        try {
            productClient.adjustProductQuantity(orderDTO.getProductId(), oldOrder.getQuantity(), orderDTO.getQuantity());
        } catch (Exception e) {
            log.error("Failed to adjust product quantity for product id: {}, rolling back order update", orderDTO.getProductId(), e);
            // Rollback to old order state
            orderRepository.save(oldOrder);
            throw new Exception("Failed to adjust product quantity, order update rolled back.", e);
        }
        log.info("Order updated successfully: {}", existing);
    }
    public void deleteOrder(Long id) throws Exception {
        log.info("Deleting order with id: {}", id);
        if (!orderRepository.existsById(id)) {
            log.warn("Order does not exist with id: {}", id);
            throw new Exception("Order does not exist with id: " + id);
        }
        orderRepository.deleteById(id);
        log.info("Order deleted successfully with id: {}", id);
    }
} 