package com.project.product_management.service;


import com.project.product_management.dto.ProductRequest;
import com.project.product_management.dto.ProductResponse;
import com.project.product_management.entity.Product;
import com.project.product_management.exception.ResourceNotFoundException;
import com.project.product_management.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    public ProductResponse getProductById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found id :" + id));

        return mapToProductResponse(product);

    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .price(product.getPrice())
                .name(product.getName())
                .category(product.getCategory())
                .description(product.getDescription())
                .stockQty(product.getStockQty())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }


    public ProductResponse createProduct(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .sku(request.getSku())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .stockQty(request.getStockQty())
                .build();
        Product saved = productRepo.save(product);
        return mapToProductResponse(saved);
    }

    public void delete(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepo.delete(product);
    }


    public ProductResponse updateProd(ProductRequest productRequest, Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQty(productRequest.getStockQty());
        product.setCategory(productRequest.getCategory());
        product.setSku(productRequest.getSku());

        Product newProduct = productRepo.save(product);
        return mapToProductResponse(newProduct);


    }

    public Page<ProductResponse> search(String category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Page<Product> products = productRepo.searchProducts(category, minPrice, maxPrice, pageable);
        return products.map(this::mapToProductResponse);
    }
}
