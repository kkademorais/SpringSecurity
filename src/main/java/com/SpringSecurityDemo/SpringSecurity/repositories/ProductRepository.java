package com.SpringSecurityDemo.SpringSecurity.repositories;

import com.SpringSecurityDemo.SpringSecurity.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// <T1,T2>
        // T1 = Tipo da Entidade -> Classe Product
        // T2 = Tipo do ID -> String
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
