package com.azuresect.service;

import java.util.List;
import com.azuresect.model.Material;
import com.azuresect.repository.MaterialRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

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

    public Material findByName(String name) {
        if (name == null) return null;
        return repository.find("lower(name) = ?1", name.toLowerCase()).firstResult();
    }

    @Transactional
    public Material create(Material material) {
        if (material.getName() == null || material.getName().isBlank()) {
            throw new WebApplicationException("Nome do material é obrigatório", Response.Status.BAD_REQUEST);
        }

        Material existing = findByName(material.getName());
        
        if (existing != null) {
            throw new WebApplicationException("Já existe uma matéria-prima com este nome.", Response.Status.CONFLICT);
        }

        repository.persist(material);
        return material;
    }

    @Transactional
    public Material update(Long id, Material data) {
        Material entity = repository.findById(id);
        
        if (entity == null) {
            throw new WebApplicationException("Material não encontrado", Response.Status.NOT_FOUND);
        }

        Material existing = repository.find("lower(name) = ?1 and id != ?2", 
            data.getName().toLowerCase(), id).firstResult();
            
        if (existing != null) {
            throw new WebApplicationException("Já existe outro material com este nome.", Response.Status.CONFLICT);
        }

        entity.setName(data.getName()); 
        entity.setDescription(data.getDescription());
        entity.setStockQuantity(data.getStockQuantity());
        
        return entity;
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}