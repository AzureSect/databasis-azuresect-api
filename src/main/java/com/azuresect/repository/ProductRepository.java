package com.azuresect.repository;

import java.util.List;

import com.azuresect.model.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public List<Product> listAllOrderByValueDesc() {
        return listAll(Sort.by("value").descending());    }

}
