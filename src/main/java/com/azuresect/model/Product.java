package com.azuresect.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public String description;

    @OneToMany(mappedBy = "product") 
    public List<ProductComposition> composition;
}
