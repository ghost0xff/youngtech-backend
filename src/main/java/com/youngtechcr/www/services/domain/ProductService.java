package com.youngtechcr.www.services.domain;

import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.storage.ProductImageFileData;
import com.youngtechcr.www.exceptions.custom.NoDataForRequestedObjectFoundException;
import com.youngtechcr.www.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Transactional(readOnly = true)
    public Product findById(Integer productId) {
        return this.productRepository
                .findById(productId)
                .orElseThrow(
                () -> new NoDataForRequestedObjectFoundException("No product with id: " + productId + " was found"));
    }

    @Transactional(readOnly = true)
    public boolean existsById(Integer productId){
        return this.productRepository.existsById(productId);
    }
}
