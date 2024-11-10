package com.example.ECommercePlatform.service;

import com.example.ECommercePlatform.dto.*;
import com.example.ECommercePlatform.entities.*;
import com.example.ECommercePlatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final  ImageService imageService;
    private final ProductImageRepository productImageRepository;
    private final CategoryRepository categoryRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;


    @Transactional
    public String createProduct(ProductCreationData data) {
        if (data.getBrand() == null || data.getPrice() <= 0 || data.getAvailableQuantity() <= 0 || data.getCategoryId() == null || data.getGenderCategory() == null || data.getDescription() == null || data.getProductImages().isEmpty()) {
            return "Vendosni te gjitha te dhenat!";
        }

        Category category = categoryRepository.findById(data.getCategoryId()).orElse(null);

        if (category == null) {
            return "Category does not exist!";
        }

        Product productCreated = Product.builder()
                .brand(data.getBrand())
                .price(data.getPrice())
                .availableQuantity(data.getAvailableQuantity())
                .description(data.getDescription())
                .category(category)
                .genderCategory(data.getGenderCategory())
                .build();

        Product savedProduct = productRepository.save(productCreated);

        List<MultipartFile> productImages = data.getProductImages();

        for (int i = 0; i < productImages.size(); i++) {

            String imageName = category.getName() + "_" + category.getSubcategory() + "_" + data.getBrand() + "_" + i + "_" + savedProduct.getId();

            String imageNameWithExtension = "";
            try {
                imageNameWithExtension = imageService.saveImage(new File("src/main/resources/static/img"), productImages.get(i), imageName);
            } catch (IOException e) {
                System.out.println("Problem saving image...");
                return "Problem saving image...";
            }

            ProductImage productImage = ProductImage.builder()
                    .product(savedProduct)
                    .imageUrl(imageNameWithExtension)
                    .build();

            productImageRepository.save(productImage);

        }

        return "Product created!";
    }

    public ResponseEntity<?> updateProduct(UpdateProductDto data) {
        Product product = productRepository.findById(data.getId()).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

            product.setPrice(data.getPrice());
            product.setAvailableQuantity(data.getQuantity());

        productRepository.save(product);
        return ResponseEntity.ok().body("Product updated!");
    }

    public ResponseEntity<?> deleteProduct(long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        Set<CartItem> cartItems = cartItemRepository.findAllByProduct(product);

        cartItems.forEach(cartItem -> {
            Cart cart = cartItem.getCart();
            cart.setQuantity(cart.getQuantity() - cartItem.getQuantity());
            cartRepository.save(cart);

            cartItemRepository.delete(cartItem);
        });

        productRepository.delete(product);
        return ResponseEntity.ok().body("Product deleted!");
    }

    public ResponseEntity<?> searchProductByBrand(String brand) {
        List<ProductResponseDto> products = productRepository.findTop5ByBrandLike("%" + brand + "%");

        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No result found!");
        }

        return ResponseEntity.ok().body(products);


    }


    public ResponseEntity<?> getAllProducts(FilterDto data) {
        Page<ProductResponseDto> pageOfProducts = productRepository.filterProducts(data.getMinPrice(), data.getMaxPrice(), data.getCategoryIds(), data.getGenderCategory(), PageRequest.of(data.getPage(), data.getSize()));

        if (pageOfProducts.getTotalElements() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found for specified filters!");
        }

        return ResponseEntity.ok().body(pageOfProducts.getContent());

    }
}





