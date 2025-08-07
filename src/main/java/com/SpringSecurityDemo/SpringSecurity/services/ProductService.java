package com.SpringSecurityDemo.SpringSecurity.services;

import com.SpringSecurityDemo.SpringSecurity.domain.product.Product;
import com.SpringSecurityDemo.SpringSecurity.domain.product.ProductRequestDTO;
import com.SpringSecurityDemo.SpringSecurity.domain.product.ProductResponseDTO;
import com.SpringSecurityDemo.SpringSecurity.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository = repository;   // Injeção de Dependência via construtor
    }


        // Serviço para POST
    public void addProduct(@RequestBody @Valid ProductRequestDTO body){
        Product product = new Product(body);
        product.setCost(product.getPrice() * product.getQuantity().doubleValue());
        this.repository.save(product);
    }

        // Serviço para GET
    public List<ProductResponseDTO> getProduct(){
        List<ProductResponseDTO> productList = this.repository.findAll().stream().map(product -> new ProductResponseDTO(product)).toList();
        return productList;
    }


}
