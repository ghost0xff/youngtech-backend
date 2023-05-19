package com.youngtechcr.www.services;

import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.exceptions.custom.EmptyRepositoryException;
import com.youngtechcr.www.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Transactional
    public List<Product> findAllProducts() {
        List<Product> productList = productRepository.findAll();
        if(!productList.isEmpty()){
            return productList;
        }
        throw new EmptyRepositoryException("No products found in repository");
    }


}
