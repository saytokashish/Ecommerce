package com.example.service;

import com.example.dto.OrderDTO;
import java.util.List;

public interface IOrderService {
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(Long id) throws Exception;
    void createOrder(OrderDTO orderDTO) throws Exception;
    void updateOrder(Long id, OrderDTO orderDTO) throws Exception;
    void deleteOrder(Long id) throws Exception;
} 