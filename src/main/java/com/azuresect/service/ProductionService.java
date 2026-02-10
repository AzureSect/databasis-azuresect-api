package com.azuresect.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.azuresect.model.Material;
import com.azuresect.model.Product;
import com.azuresect.model.ProductComposition;
import com.azuresect.repository.MaterialRepository;
import com.azuresect.repository.ProductRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProductionService {

    @Inject
    ProductRepository productRepository;

    @Inject
    MaterialRepository materialRepository;

    public void calculateProduction() {

        List<Product> products =
                productRepository.find("order by value desc").list();

        Map<Long, Integer> stock = new HashMap<>();

        for (Material m : materialRepository.listAll()) {
            stock.put(m.id, m.stockQuantity);
        }

        // 3.  produtos
        for (Product p : products) {

            int maxUnits = Integer.MAX_VALUE;

            for (ProductComposition comp : p.composition) {

                Integer available = stock.get(comp.material.id);

                int possible = available / comp.quantityNeeded;

                maxUnits = Math.min(maxUnits, possible);
            }

            System.out.println("Produto: " + p.name + " -> " + maxUnits);
        }
    }
}

