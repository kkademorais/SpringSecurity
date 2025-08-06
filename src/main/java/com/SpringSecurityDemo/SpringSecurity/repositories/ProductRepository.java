package com.SpringSecurityDemo.SpringSecurity.repositories;

import com.SpringSecurityDemo.SpringSecurity.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
