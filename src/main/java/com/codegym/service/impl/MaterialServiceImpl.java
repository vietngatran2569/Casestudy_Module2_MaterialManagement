package com.codegym.service.impl;

import com.codegym.model.Material;
import com.codegym.model.Supplier;
import com.codegym.repository.MaterialRepository;
import com.codegym.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class MaterialServiceImpl implements MaterialService {
    @Autowired
    MaterialRepository materialRepository;
    @Override
    public Page<Material> findAll(Pageable pageable) {
        return materialRepository.findAll(pageable);
    }

    @Override
    public Material findById(Long id) {
        return materialRepository.findById(id).get();
    }

    @Override
    public void save(Material material) {
         materialRepository.save(material);
    }

    @Override
    public void remove(Long id) {
          materialRepository.deleteById(id);
    }

    @Override
    public Page<Material> findAllByNameContaining(String name, Pageable pageable) {
        return materialRepository.findAllByNameContaining(name,pageable);
    }

    @Override
    public Page<Material> findAllBySupplier(Pageable pageable, Supplier supplier) {
        return materialRepository.findAllBySupplier(pageable,supplier);
    }

    @Override
    public Page<Material> findAllByOrderByPriceAsc(Pageable pageable) {
        return materialRepository.findAllByOrderByPriceAsc(pageable);
    }

    @Override
    public Page<Material> findAllByOrderByPriceDesc(Pageable pageable) {
        return materialRepository.findAllByOrderByPriceDesc(pageable);
    }

    @Override
    public Iterable<Material> findAllBySupplier(Supplier supplier) {
        return materialRepository.findAllBySupplier(supplier);
    }

}
