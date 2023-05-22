package com.youngtechcr.www.services.domain;

import com.youngtechcr.www.domain.Brand;
import com.youngtechcr.www.exceptions.custom.AlreadyExistsException;
import com.youngtechcr.www.exceptions.custom.EmptyRepositoryException;
import com.youngtechcr.www.exceptions.custom.NoDataForRequestedObjectFoundException;
import com.youngtechcr.www.exceptions.custom.ParameterValueAndRequestBodyMismatchException;
import com.youngtechcr.www.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Transactional(readOnly = true)
    public List<Brand> findAllBrands() {
        List<Brand> retrievedBrandList = this.brandRepository.findAll();
        if(!retrievedBrandList.isEmpty()){
            return retrievedBrandList;
        } else {
            throw new EmptyRepositoryException("No brands found in storage");
        }
    }

    @Transactional( readOnly = true)
    public Brand findById(Integer brandId){
        Brand requestedBrand = new Brand(brandId);
        Brand retrievedBrand = this
                .brandRepository
                .findById(requestedBrand.getBrandId())
                .orElseThrow(() -> new
                        NoDataForRequestedObjectFoundException("No brand with id: " + requestedBrand.getBrandId() + " was found"));
        return retrievedBrand;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Brand createBrand(Brand brandToBeCreated) {
        if(!this.existsByName(brandToBeCreated)) {
            return this.brandRepository.save(brandToBeCreated);
        } else {
            throw new AlreadyExistsException(
                    "Can't create brand with name "
                    + brandToBeCreated.getName() +
                    " because it already exists another brand with the requested name");
        }
    }

    @Transactional(readOnly = true)
    public Brand findByName(Brand requestedBrand){
        Optional<Brand> searchedBrand = this.brandRepository.findByName(requestedBrand.getName());
        return searchedBrand.orElseThrow(() -> new
                NoDataForRequestedObjectFoundException("No brand with name:" + requestedBrand.getName() + " was found"));
    }


    @Transactional
    public void deleteBrandById(Integer brandId) {
        Brand requestedBrand = new Brand(brandId);
        if(this.existsById(requestedBrand)){
            this.brandRepository.deleteById(requestedBrand.getBrandId());
        } else {
            throw new NoDataForRequestedObjectFoundException("No brand with id: " + requestedBrand.getBrandId() + " was found");
        }
    }

    @Transactional
    public Brand updateBrandById(Integer brandId, Brand brandToBeUpdated) {
        Brand requestedBrand = new Brand(brandId);
        if(!brandToBeUpdated.getBrandId().equals(brandId)){
            throw new ParameterValueAndRequestBodyMismatchException("Both, id from path parameter and id from request body have to be the same");
        }
        if(this.existsById(requestedBrand)) {
            return this.brandRepository.save(brandToBeUpdated);
        } else {
            throw new NoDataForRequestedObjectFoundException("No brand with id: " + requestedBrand.getBrandId() + " was found");
        }
    }

    @Transactional(readOnly = true)
    public boolean existsByName(Brand requestedBrand) {
        return this.brandRepository.existsByName(requestedBrand.getName());
    }

    @Transactional(readOnly = true)
    public boolean existsById(Brand requestedBrand) {
        return this.brandRepository.existsById(requestedBrand.getBrandId());
    }

}
