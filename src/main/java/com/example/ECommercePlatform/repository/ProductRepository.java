package com.example.ECommercePlatform.repository;
import com.example.ECommercePlatform.dto.ProductResponseDto;
import com.example.ECommercePlatform.entities.GenderCategory;
import com.example.ECommercePlatform.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String categoryName);

    List<ProductResponseDto> findTop5ByBrandLike(String brand);

    @Query("SELECT p FROM Product p WHERE " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:genderCategory IS NULL OR p.genderCategory = :genderCategory) AND " +
            "(COALESCE(:categoryIds, NULL) IS NULL OR p.category.id IN :categoryIds)")
    Page<ProductResponseDto> filterProducts(@Param("minPrice") Double minPrice,
                                            @Param("maxPrice") Double maxPrice,
                                            @Param("categoryIds") List<Long> categoryIds,
                                            @Param("genderCategory") GenderCategory genderCategory,
                                            Pageable pageable);
}

