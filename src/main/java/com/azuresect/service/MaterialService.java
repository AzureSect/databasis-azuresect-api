package com.azuresect.service;

import java.util.List;
import com.azuresect.model.Material;
import com.azuresect.repository.MaterialRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MaterialService {

    @Inject
    MaterialRepository repository;

    public List<Material> listAll() {
        return repository.listAll();
    }

    public Material findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Material create(Material material) {
        repository.persist(material);
        return material;
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public Material update(Long id, Material data) {
        Material entity = repository.findById(id);
        if (entity == null) throw new RuntimeException("Material não encontrado");
        entity.name = data.name;
        entity.description = data.description;
        entity.stockQuantity = data.stockQuantity;
        return entity;
    }   
}
