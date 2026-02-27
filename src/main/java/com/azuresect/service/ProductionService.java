package com.azuresect.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

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

    public Map<String, Object> calculateProduction() {
        List<Product> products = productRepository.listAllOrderByValueDesc();

        Map<Long, Integer> stock = new HashMap<>();
        for (Material m : materialRepository.listAll()) {
            stock.put(m.getId(), m.getStockQuantity());
        }

        double totalProductionValue = 0;
        List<Map<String, Object>> itensSugeridos = new ArrayList<>();

        for (Product p : products) {
            if (p.getComposition() == null || p.getComposition().isEmpty()) continue;

            int maxUnits = Integer.MAX_VALUE;

            for (ProductComposition comp : p.getComposition()) {
                Integer available = stock.getOrDefault(comp.getMaterial().getId(), 0);
                int possible = available / comp.getQuantityNeeded();
                maxUnits = Math.min(maxUnits, possible);
            }

            if (maxUnits > 0) {
                double productTotalValue = maxUnits * p.getValue();
                totalProductionValue += productTotalValue;

                Map<String, Object> item = new HashMap<>();
                item.put("produto", p.getName());
                item.put("quantidade", maxUnits);
                item.put("valorTotal", productTotalValue);
                
                List<Map<String, Object>> materiaisDesteProduto = new ArrayList<>();
                for (ProductComposition comp : p.getComposition()) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("nome", comp.getMaterial().getName());
                    m.put("gastoUnitario", comp.getQuantityNeeded());
                    m.put("gastoTotal", comp.getQuantityNeeded() * maxUnits);
                    materiaisDesteProduto.add(m);

                    int currentStock = stock.get(comp.getMaterial().getId());
                    stock.put(comp.getMaterial().getId(), currentStock - (comp.getQuantityNeeded() * maxUnits));
                }
                item.put("materiaisUtilizados", materiaisDesteProduto);
                itensSugeridos.add(item);
            }
        } 

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("sugestoes", itensSugeridos);
        resultado.put("valorTotalGeral", totalProductionValue);
        
        return resultado;
    }
}