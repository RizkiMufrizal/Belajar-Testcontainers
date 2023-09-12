package com.belajar.testcontainers.service;

import com.belajar.testcontainers.domain.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    Product save(Product product);

    Optional<Product> findOne(UUID id);
}
