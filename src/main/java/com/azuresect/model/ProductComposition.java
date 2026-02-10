package com.azuresect.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class ProductComposition {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    public Product product;

    @ManyToOne
    @JoinColumn(name = "material_id")
    public Material material;

    public Integer quantityNeeded;
}
