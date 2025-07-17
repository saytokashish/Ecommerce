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

import java.util.List;

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
        return orderRepository.findAll().stream().map(this::toDTO).toList();
    }
    public OrderDTO getOrderById(Long id) throws Exception {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) throw new Exception("Order does not exist with id: " + id);
        return toDTO(order);
    }
    public void createOrder(OrderDTO orderDTO) throws Exception {
        if (!userClient.userExists(orderDTO.getUserId())) {
            throw new Exception("User does not exist with id: " + orderDTO.getUserId());
        }
        if (!productClient.productExists(orderDTO.getProductId())) {
            throw new Exception("Product does not exist with id: " + orderDTO.getProductId());
        }
        Order order = toEntity(orderDTO);
        order.setId(null);
        orderRepository.save(order);
    }
    public void updateOrder(Long id, OrderDTO orderDTO) throws Exception {
        Order existing = orderRepository.findById(id).orElse(null);
        if (existing == null) throw new Exception("Order does not exist with id: " + id);
        if (!userClient.userExists(orderDTO.getUserId())) {
            throw new Exception("User does not exist with id: " + orderDTO.getUserId());
        }
        if (!productClient.productExists(orderDTO.getProductId())) {
            throw new Exception("Product does not exist with id: " + orderDTO.getProductId());
        }
        existing.setUserId(orderDTO.getUserId());
        existing.setProductId(orderDTO.getProductId());
        existing.setQuantity(orderDTO.getQuantity());
        existing.setStatus(orderDTO.getStatus());
        orderRepository.save(existing);
    }
    public void deleteOrder(Long id) throws Exception {
        if (!orderRepository.existsById(id)) throw new Exception("Order does not exist with id: " + id);
        orderRepository.deleteById(id);
    }
} 