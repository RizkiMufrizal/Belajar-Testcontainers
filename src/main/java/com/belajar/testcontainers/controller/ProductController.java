package com.belajar.testcontainers.controller;

import com.belajar.testcontainers.domain.Product;
import com.belajar.testcontainers.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<?> findOne(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(productService.findOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/products")
    public ResponseEntity<?> save(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.OK);
    }

}
