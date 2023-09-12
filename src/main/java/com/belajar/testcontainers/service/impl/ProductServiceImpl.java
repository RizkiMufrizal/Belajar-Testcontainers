package com.belajar.testcontainers.service.impl;

import com.belajar.testcontainers.domain.Product;
import com.belajar.testcontainers.repository.ProductRepository;
import com.belajar.testcontainers.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> findOne(UUID id) {
        return productRepository.findById(id);
    }
}