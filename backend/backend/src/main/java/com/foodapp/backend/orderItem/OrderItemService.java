package com.foodapp.backend.orderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getAllItemByOrderId(Integer orderId){

        return orderItemRepository.findByOrderId(orderId);
    }

    public OrderItem addOrderItem(OrderItem orderItem){
        orderItemRepository.save(orderItem);
        return orderItem;
    }
}
