package com.foodapp.backend.order;

import com.foodapp.backend.orderItem.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrder(){
        return orderRepository.findAllWithItems();
    }

    public Order getOrderByid(Integer orderid) {
        return orderRepository.findByIdWithItems(orderid);
    }

    public Order addOrder(Order order) {
        if (order.getIdKhachHang() == null) {
            throw new IllegalStateException("Ma Khach Hang cannot be null");
        }
        
        if (order.getThoiGianTao() == null) {
            order.setThoiGianTao(new java.util.Date());
        }
        // Link order to its items
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                item.setOrder(order);
            }
        }

        return orderRepository.save(order);
    }

}
