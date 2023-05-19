package com.youngtechcr.www.services;

import com.youngtechcr.www.domain.Sale;
import com.youngtechcr.www.exceptions.custom.EmptyRepositoryException;
import com.youngtechcr.www.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Transactional(readOnly = true)
    public List<Sale> findAllBrands(){
        List<Sale> saleList = saleRepository.findAll();
        if(!saleList.isEmpty()) {
            return saleList;
        }
        throw new EmptyRepositoryException("No sales found in repository");
    }

    @Transactional
    public void saveSale(Sale sale) {
        saleRepository.save(sale);
    }

    @Transactional
    public void deleteSale(Sale sale) {
        saleRepository.delete(sale);
    }
}
