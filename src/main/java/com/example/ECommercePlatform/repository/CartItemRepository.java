package com.example.ECommercePlatform.repository;

import com.example.ECommercePlatform.entities.CartItem;
import com.example.ECommercePlatform.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Set<CartItem> findAllByProduct(Product product);
}
