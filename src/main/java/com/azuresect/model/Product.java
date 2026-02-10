package com.azuresect.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public String description;

    public double value;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true) 
    @JsonManagedReference
    public List<ProductComposition> composition;
}