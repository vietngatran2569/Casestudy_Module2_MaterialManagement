package com.codegym.service;

import com.codegym.model.Material;
import com.codegym.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MaterialService {
    Page<Material> findAll(Pageable pageable);

    Material findById(Long id);

    void save(Material material);

    void remove(Long id);

    Page<Material> findAllByNameContaining(String name, Pageable pageable);
    Iterable<Material> findAllBySupplier(Supplier supplier);

    Page<Material> findAllBySupplier(Pageable pageable, Supplier supplier);

    Page<Material> findAllByOrderByPriceAsc(Pageable pageable);

    Page<Material> findAllByOrderByPriceDesc(Pageable pageable);

}
