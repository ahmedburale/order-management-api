package com.orderapi.service;

import com.orderapi.dto.*;
import com.orderapi.entity.Order;
import com.orderapi.enums.OrderStatus;
import com.orderapi.exception.OrderNotFoundException;
import com.orderapi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public List<OrderResponse> listOrders(OrderStatus status) {
        List<Order> orders = (status != null)
                ? orderRepository.findAllByStatus(status)
                : orderRepository.findAll();
        log.info("Listing {} orders (status filter: {})", orders.size(), status);
        return orders.stream().map(this::toResponse).toList();
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest req) {
        Order order = Order.builder()
                .customerName(req.getCustomerName())
                .product(req.getProduct())
                .quantity(req.getQuantity())
                .build();
        Order saved = orderRepository.save(order);
        log.info("Order created with id={}", saved.getId());
        return toResponse(saved);
    }

    public OrderSummaryResponse getSummary() {
        long total    = orderRepository.count();
        long pending  = orderRepository.countByStatus(OrderStatus.PENDING);
        long processing = orderRepository.countByStatus(OrderStatus.PROCESSING);
        long shipped  = orderRepository.countByStatus(OrderStatus.SHIPPED);
        long delivered = orderRepository.countByStatus(OrderStatus.DELIVERED);
        long cancelled = orderRepository.countByStatus(OrderStatus.CANCELLED);
        log.info("Fetching order summary: total={}", total);
        return OrderSummaryResponse.builder()
                .total(total)
                .pending(pending)
                .processing(processing)
                .shipped(shipped)
                .delivered(delivered)
                .cancelled(cancelled)
                .build();
    }

    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return toResponse(order);
    }

    @Transactional
    public OrderResponse updateOrder(Long id, UpdateOrderRequest req) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        if (req.getCustomerName() != null) order.setCustomerName(req.getCustomerName());
        if (req.getProduct()      != null) order.setProduct(req.getProduct());
        if (req.getQuantity()     != null) order.setQuantity(req.getQuantity());
        if (req.getStatus()       != null) order.setStatus(req.getStatus());
        Order saved = orderRepository.save(order);
        log.info("Order updated id={} status={}", saved.getId(), saved.getStatus());
        return toResponse(saved);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.delete(order);
        log.info("Order deleted id={}", id);
    }

    private OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .product(order.getProduct())
                .quantity(order.getQuantity())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
