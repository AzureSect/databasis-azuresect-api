package com.azuresect.service;

import java.util.List;
import com.azuresect.model.Product;
import com.azuresect.model.ProductComposition;
import com.azuresect.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository repository;

    public Product findByName(String name) {
        return repository.find("name", name).firstResult();
    }

    @Transactional
    public Product create(Product product) {
        if (product.getName() == null || product.getName().isEmpty())
            throw new WebApplicationException("Nome do produto é obrigatório", Response.Status.BAD_REQUEST);

        if (product.getValue() <= 0)
            throw new WebApplicationException("Valor do produto deve ser maior que zero", Response.Status.BAD_REQUEST);

        Product existing = findByName(product.getName());
        if (existing != null) {
            throw new WebApplicationException("Já existe um produto com este nome.", Response.Status.CONFLICT);
        }

        if (product.getComposition() != null) {
            for (ProductComposition comp : product.getComposition()) {
                if (comp.getMaterial() == null) throw new WebApplicationException("Material não informado", Response.Status.BAD_REQUEST);
                if (comp.getQuantityNeeded() == null || comp.getQuantityNeeded() <= 0)
                    throw new WebApplicationException("Quantidade necessária inválida", Response.Status.BAD_REQUEST);
                comp.setProduct(product);
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
        if (product == null) throw new WebApplicationException("Produto não encontrado", Response.Status.NOT_FOUND);

        Product existing = repository.find("name = ?1 and id != ?2", data.getName(), id).firstResult();
        if (existing != null) {
            throw new WebApplicationException("Já existe outro produto com este nome.", Response.Status.CONFLICT);
        }

        product.setName(data.getName());
        product.setValue(data.getValue());
        product.setDescription(data.getDescription());

        if (data.getComposition() != null) {
            product.getComposition().clear();
            for (ProductComposition comp : data.getComposition()) {
                if (comp.getMaterial() == null) throw new WebApplicationException("Material não informado", Response.Status.BAD_REQUEST);
                if (comp.getQuantityNeeded() == null || comp.getQuantityNeeded() <= 0)
                    throw new WebApplicationException("Quantidade necessária inválida", Response.Status.BAD_REQUEST);
                comp.setProduct(product);
                product.getComposition().add(comp);
            }
        }

        return product;
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}