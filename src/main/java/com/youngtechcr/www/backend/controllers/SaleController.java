package com.youngtechcr.www.backend.controllers;

import com.youngtechcr.www.backend.domain.Sale;
import com.youngtechcr.www.backend.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Sale>> listAllSales() {
        List<Sale> saleList = saleService.findAllBrands();
        return ResponseEntity.ok().body(saleList);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createSale(@RequestBody @Validated Sale sale) {
        saleService.saveSale(sale);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateSale(@RequestBody @Validated Sale sale) {
        saleService.saveSale(sale);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteSale(@RequestBody Sale sale) {
        saleService.deleteSale(sale);
    }

}
