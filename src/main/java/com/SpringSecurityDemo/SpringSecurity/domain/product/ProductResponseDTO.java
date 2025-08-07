package com.SpringSecurityDemo.SpringSecurity.domain.product;


public record ProductResponseDTO(String id, String name, Integer quantity, Double cost) {
    public ProductResponseDTO(Product product){
        this(product.getId(), product.getName(), product.getQuantity(), product.getCost());
    }
}
