package com.example.ECommercePlatform.service;

import com.example.ECommercePlatform.dto.CartItemDto;
import com.example.ECommercePlatform.entities.Cart;
import com.example.ECommercePlatform.entities.CartItem;
import com.example.ECommercePlatform.entities.Product;
import com.example.ECommercePlatform.repository.CartItemRepository;
import com.example.ECommercePlatform.repository.CartRepository;
import com.example.ECommercePlatform.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;


    public ResponseEntity<?> addToCart(CartItemDto data) {
        Product productFromDb = productRepository.findById(data.getProductId()).orElse(null);

        if (productFromDb == null) {
            return ResponseEntity.notFound().build();
        }

        Cart cart = cartRepository.findById(data.getCartId()).orElse(null);

        List<CartItem> savedCartItemsFromDb = cartItemRepository.findAll();

        CartItem existingCartItem = savedCartItemsFromDb.stream().filter(cartItem -> cartItem.getProduct().getId() == data.getProductId()).findFirst().orElse(null);

        if (existingCartItem != null) {

            if (productFromDb.getAvailableQuantity() < (data.getQuantity() + existingCartItem.getQuantity())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Not enough available quantity");
            }
            existingCartItem.setQuantity(existingCartItem.getQuantity() + data.getQuantity());
            cartItemRepository.save(existingCartItem);

        } else {

            if (productFromDb.getAvailableQuantity() < data.getQuantity()) {
                return ResponseEntity.badRequest().body("Not enough available quantity");
            }
            CartItem cartItemToBeSaved = CartItem.builder()
                    .product(productFromDb)
                    .quantity(data.getQuantity())
                    .cart(cart)
                    .build();

            cartItemRepository.save(cartItemToBeSaved);
        }


        cart.setQuantity(cart.getQuantity() + data.getQuantity());
        cartRepository.save(cart);

        //fshije
      //  cartItemRepository.findAllByProduct(productFromDb, PageRequest.of(1,5));



        return ResponseEntity.status(HttpStatus.OK).body("Item added to cart successfully");
    }


    public ResponseEntity<?> removeFromCart(long id) {
        CartItem cartItemFromDb = cartItemRepository.findById(id).orElse(null);

        if (cartItemFromDb == null) {
            return ResponseEntity.notFound().build();
        }

        Cart cart = cartItemFromDb.getCart();
        cart.setQuantity(cart.getQuantity()-cartItemFromDb.getQuantity());

        cartItemRepository.delete(cartItemFromDb);
        return ResponseEntity.status(HttpStatus.OK).body("Item deleted from cart successfully");
    }

}
