package com.SpringSecurityDemo.SpringSecurity.controllers;

import com.SpringSecurityDemo.SpringSecurity.domain.product.ProductRequestDTO;
import com.SpringSecurityDemo.SpringSecurity.domain.product.ProductResponseDTO;
import com.SpringSecurityDemo.SpringSecurity.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController()
@RequestMapping("/products")
public class ProductController {


    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;   // Injeção de Dependência via Construtor
    }

    @PostMapping
    public ResponseEntity postProduct(@RequestBody @Valid ProductRequestDTO body){
        this.service.addProduct(body);
        return ResponseEntity.ok().build(); // Resposta HTTP do POST-
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProduct(){
        List<ProductResponseDTO> productList = this.service.getProduct();
        return ResponseEntity.ok(productList);  // Resposta HTTP do GET
    }


}
