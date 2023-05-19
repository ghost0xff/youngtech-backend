package com.youngtechcr.www.backend.services;

import com.youngtechcr.www.backend.domain.Brand;
import com.youngtechcr.www.backend.exceptions.custom.EmptyRepositoryException;
import com.youngtechcr.www.backend.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Transactional(readOnly = true)
    public List<Brand> findAllBrands() {
        List<Brand> brandList = brandRepository.findAll();
        if(!brandList.isEmpty()) {
            return brandList;
        }
        throw new EmptyRepositoryException("No brands found in repository");
    }

    @Transactional
    public void saveBrand(Brand brand) {
        brandRepository.save(brand);
    }

    @Transactional
    public void deleteBrand(Brand brand) {
        brandRepository.delete(brand);
    }

}
