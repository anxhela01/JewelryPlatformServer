package com.example.ECommercePlatform.service;

import com.example.ECommercePlatform.dto.OrderCreationDto;
import com.example.ECommercePlatform.dto.OrderItemDto;
import com.example.ECommercePlatform.entities.Order;
import com.example.ECommercePlatform.entities.OrderItem;
import com.example.ECommercePlatform.entities.Product;
import com.example.ECommercePlatform.entities.User;
import com.example.ECommercePlatform.repository.OrderItemRepository;
import com.example.ECommercePlatform.repository.OrderRepository;
import com.example.ECommercePlatform.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;


    @Transactional
    public ResponseEntity<?> createOrder(OrderCreationDto data) {

        List<OrderItemDto> orderItems = data.getOrderItems();
        List<Product> products = new ArrayList<>();
        User buyer = userService.getAuthenticatedUser();


        boolean canContinue = true;
        for (OrderItemDto orderItem : orderItems) {
            Product productFromDb = productRepository.findById(orderItem.getProductId()).orElse(null);
            products.add(productFromDb);

            if (productFromDb == null) {
                return ResponseEntity.badRequest().body("Product not found");
            }
            if (orderItem.getQuantity() > productFromDb.getAvailableQuantity()) {
                canContinue = false;
                break;
            }
        }


        if (canContinue) {
            Order order = Order.builder()
                    .date(LocalDateTime.now())
                    .shippingAddress(data.getShippingAddress())
                    .user(buyer)
                    .build();

            Order savedOrder = orderRepository.save(order);

            for (OrderItemDto orderItem : orderItems) {

                Product productToModify = products.stream().filter(product -> product.getId() == orderItem.getProductId()).findFirst().get();

                Double subtotal = orderItem.getQuantity() * productToModify.getPrice();

                productToModify.setAvailableQuantity(productToModify.getAvailableQuantity() - orderItem.getQuantity());
                productRepository.save(productToModify);

                OrderItem toBeSaved = OrderItem.builder()
                        .order(savedOrder)
                        .product(productToModify)
                        .subtotal(subtotal)
                        .price(productToModify.getPrice())
                        .quantity(orderItem.getQuantity())
                        .build();

                orderItemRepository.save(toBeSaved);

            }

            return ResponseEntity.ok().body("Order created");
        } else {
            return ResponseEntity.badRequest().body("Order not created");
        }


    }
}

