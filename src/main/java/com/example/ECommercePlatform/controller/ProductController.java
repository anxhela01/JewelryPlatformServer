package com.example.ECommercePlatform.controller;

import com.example.ECommercePlatform.dto.*;
import com.example.ECommercePlatform.entities.Product;
import com.example.ECommercePlatform.repository.ProductRepository;
import com.example.ECommercePlatform.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/create-product", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String createProduct(@ModelAttribute ProductCreationData data) {
        System.out.println(data);
        return productService.createProduct(data);
    }

    @PutMapping("/admin/update-product")
    public ResponseEntity<?> updateProduct(@RequestBody UpdateProductDto data) {
        System.out.println(data);
        return productService.updateProduct(data);
    }

    @DeleteMapping("/admin/delete-product")
    public ResponseEntity<?> deleteProduct(@RequestParam("id") long id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/product-search")
    public ResponseEntity<?> searchProductByBrand(@RequestParam("query") String brand) {
        return productService.searchProductByBrand(brand);
    }



    @PostMapping("/all")
    public ResponseEntity<?> getAllProducts(@RequestBody FilterDto data) {

        return productService.getAllProducts(data);

    }


}



