package com.youngtechcr.www.brand;

import com.youngtechcr.www.product.Product;
import com.youngtechcr.www.exceptions.custom.AlreadyExistsException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.exceptions.custom.ValueMismatchException;
import com.youngtechcr.www.http.BasicCrudService;
import com.youngtechcr.www.product.ProductService;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.domain.TimestampedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BrandService implements BasicCrudService<Brand> {

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ProductService productService;

    private static final Logger log = LoggerFactory.getLogger(BrandService.class);

    @Override
    @Transactional(readOnly = true)
    public Brand findById(Integer brandId) {
        return this
                .brandRepository
                .findById(brandId)
                .orElseThrow(() -> new NoDataFoundException(HttpErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND));
    }

    @Transactional
    public Brand create(Brand brandToBeCreated) {
        if (!this.brandRepository.existsByName(brandToBeCreated.getName())) {
            TimestampedUtils.setTimestampsToNow(brandToBeCreated);
            Brand newBrand = this.brandRepository.save(brandToBeCreated);
            log.info("Created new brand: " + newBrand);
            return newBrand;
        }
        throw new AlreadyExistsException(HttpErrorMessages.CANT_CREATE_DUPLICATE_NAME);
    }

    @Override
    @Transactional
    public Brand updateById(Integer brandId, Brand brandToBeUpdated) {
        if (brandId.equals(brandToBeUpdated.getBrandId())) {
            LocalDateTime storedCreatedAtTimeStamp = this.findById(brandId).getCreatedAt();
            TimestampedUtils.updateTimeStamps(brandToBeUpdated, storedCreatedAtTimeStamp);
            Brand updatedBrand = this.brandRepository.save(brandToBeUpdated);
            log.info("Updated brand: " + updatedBrand);
            return updatedBrand;
        }
        throw new ValueMismatchException(HttpErrorMessages.PROVIDED_IDS_DONT_MATCH);
    }

    @Override
    @Transactional
    public void deleteById(Integer brandId) {
        if (this.brandRepository.existsById(brandId)) {
            this.brandRepository.deleteById(brandId);
            log.info("Deleted Brand with id: " + brandId);
            return;
        }
        throw new NoDataFoundException(HttpErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
    }

    @Transactional(readOnly = true)
    public List<Product> findAllProductsByBrandId(Integer brandId) {
        var requestedBrand = this.findById(brandId);
        return requestedBrand.getProductList();
    }

    @Transactional(readOnly = true)
    public Product findProductByBrandId(Integer brandId, Integer productId) {
        Brand requestedBrand = this.findById(brandId);
        Product requestedProduct = this.productService.findProductById(productId);
        if(requestedProduct.getBrand().equals(requestedBrand)) {
            return requestedProduct;
        }
        throw new ValueMismatchException(HttpErrorMessages.REQUESTED_CHILD_ELEMENT_DOESNT_EXIST);
    }

}
