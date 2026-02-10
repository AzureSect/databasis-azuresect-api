package com.azuresect.service;

import java.util.List;
import com.azuresect.model.Product;
import com.azuresect.model.ProductComposition;
import com.azuresect.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository repository;

    @Transactional
    public Product create(Product product) {
        if (product.name == null || product.name.isEmpty())
            throw new RuntimeException("Nome do produto é obrigatório");

        if (product.value <= 0)
            throw new RuntimeException("Valor do produto deve ser maior que zero");

        if (product.composition != null) {
            for (ProductComposition comp : product.composition) {
                if (comp.material == null) throw new RuntimeException("Material não informado");
                if (comp.quantityNeeded == null || comp.quantityNeeded <= 0)
                    throw new RuntimeException("Quantidade necessária inválida");
                comp.product = product;
            }
        }

        repository.persist(product);
        return product;
    }

    public List<Product> listAll() {
        return repository.listAll();
    }

    @Transactional
    public Product update(Long id, Product data) {
        Product product = repository.findById(id);
        if (product == null) throw new RuntimeException("Produto não encontrado");

        product.name = data.name;
        product.value = data.value;
        product.description = data.description;

        if (data.composition != null) {
            product.composition.clear();
            for (ProductComposition comp : data.composition) {
                if (comp.material == null) throw new RuntimeException("Material não informado");
                if (comp.quantityNeeded == null || comp.quantityNeeded <= 0)
                    throw new RuntimeException("Quantidade necessária inválida");
                comp.product = product;
                product.composition.add(comp);
            }
        }

        return product;
    }
}
