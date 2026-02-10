package com.azuresect.model;

import jakarta.persistence.*;

@Entity
public class ProductComposition {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product product;

    @ManyToOne
    @JoinColumn(name = "material_id")
    public Material material;

    public Integer quantityNeeded;
}
