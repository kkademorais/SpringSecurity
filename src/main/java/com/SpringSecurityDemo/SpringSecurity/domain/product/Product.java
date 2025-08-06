package com.SpringSecurityDemo.SpringSecurity.domain.product;

import jakarta.persistence.*;

@Table(name = "product")
@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String id;
    private String name;
    private Integer price;

    public Product(){}
    public Product(ProductRequestDTO data){
        this.name = data.name();
        this.price = data.price();
    }

    public String getId() {return id;}
    public String getName() {return name;}
    public Integer getPrice() {return price;}

    public void setId(String id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setPrice(Integer price) {this.price = price;}

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }


}
