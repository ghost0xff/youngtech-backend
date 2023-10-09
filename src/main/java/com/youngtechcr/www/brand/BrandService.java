package com.youngtechcr.www.brand;

import com.youngtechcr.www.product.Product;
import com.youngtechcr.www.exceptions.custom.AlreadyExistsException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.exceptions.custom.ValueMismatchException;
import com.youngtechcr.www.http.BasicCrudService;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.domain.TimestampedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    private static final Logger log = LoggerFactory.getLogger(BrandService.class);

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    @Transactional(readOnly = true)
    public Brand find(Product product) {
        return null;
    }

    @Transactional(readOnly = true)
    public Brand find(Integer brandId) {
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


    @Transactional
    public Brand update(Integer brandId, Brand brandToBeUpdated) {
        if (brandId.equals(brandToBeUpdated.getId())) {
            LocalDateTime storedCreatedAtTimeStamp = this.find(brandId).getCreatedAt();
            TimestampedUtils.updateTimeStamps(brandToBeUpdated, storedCreatedAtTimeStamp);
            Brand updatedBrand = this.brandRepository.save(brandToBeUpdated);
            log.info("Updated brand: " + updatedBrand);
            return updatedBrand;
        }
        throw new ValueMismatchException(HttpErrorMessages.PROVIDED_IDS_DONT_MATCH);
    }


    @Transactional
    public void delete(Integer brandId) {
        if (existsById(brandId)) {
            this.brandRepository.deleteById(brandId);
            log.info("Deleted Brand with id: " + brandId);
            return;
        }
        throw new NoDataFoundException(HttpErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
    }

    @Transactional(readOnly = true)
    public List<Product> findAllProductsByBrandId(Integer brandId) {
        var requestedBrand = this.find(brandId);
        return requestedBrand.getProducts();
    }

    @Transactional(readOnly = true)
    public boolean existsById(Integer brandId) {
        return brandRepository.existsById(brandId);
    }

}
