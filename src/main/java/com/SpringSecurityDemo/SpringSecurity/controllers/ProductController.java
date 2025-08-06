package com.SpringSecurityDemo.SpringSecurity.controllers;

import com.SpringSecurityDemo.SpringSecurity.domain.product.Product;
import com.SpringSecurityDemo.SpringSecurity.domain.product.ProductRequestDTO;
import com.SpringSecurityDemo.SpringSecurity.domain.product.ProductResponseDTO;
import com.SpringSecurityDemo.SpringSecurity.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.ArrayList;

@RestController()
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductRepository repository;

    @PostMapping
    public ResponseEntity postProduct(@RequestBody @Valid ProductRequestDTO body){
        Product newProduct = new Product(body);

        this.repository.save(newProduct);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getProduct(@RequestBody @Valid ProductController body){
        List<ProductResponseDTO> productList = this.repository.findAll().stream().map(ProductResponseDTO::new).toList();

        return ResponseEntity.ok(productList);
    }


}
