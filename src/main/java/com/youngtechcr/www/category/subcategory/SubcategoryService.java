package com.youngtechcr.www.category.subcategory;

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

@Service
public class SubcategoryService implements BasicCrudService<Subcategory> {

    private final SubcategoryRepository subcategoryRepository;
    private static final Logger log = LoggerFactory.getLogger(SubcategoryService.class);

    public SubcategoryService(SubcategoryRepository subcategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
    }

    @Override
    public Subcategory findById(Integer subcategoryId) {
        return this
                .subcategoryRepository
                .findById(subcategoryId)
                .orElseThrow( () -> new NoDataFoundException(HttpErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND));
    }

    @Override
    public Subcategory create(Subcategory subcategoryToBeCreated) {
        if(!this.subcategoryRepository.existsByName(subcategoryToBeCreated.getName())) {
            TimestampedUtils.setTimestampsToNow(subcategoryToBeCreated);
            Subcategory createdSubcategory = this.subcategoryRepository.save(subcategoryToBeCreated);
            return createdSubcategory;
        }
        throw new AlreadyExistsException(HttpErrorMessages.CANT_CREATE_DUPLICATE_NAME);
    }

    @Override
    public Subcategory updateById(Integer subcategoryId, Subcategory subcategoryToBeUpdated) {
        if(subcategoryId.equals(subcategoryToBeUpdated.getId())){
            LocalDateTime storedCreatedAtTimestamp = this.findById(subcategoryId).getCreatedAt();
            TimestampedUtils.updateTimeStamps(subcategoryToBeUpdated, storedCreatedAtTimestamp);
            var updatedSubcategory = this.subcategoryRepository.save(subcategoryToBeUpdated);
            return updatedSubcategory;
        }
        throw new ValueMismatchException(HttpErrorMessages.PROVIDED_IDS_DONT_MATCH);
    }

    @Override
    public void deleteById(Integer categoryId) {
        if(this.subcategoryRepository.existsById(categoryId)) {
            this.subcategoryRepository.deleteById(categoryId);
            return;
        }
        throw new NoDataFoundException(HttpErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
    }


    @Transactional(readOnly = true)
    public boolean existsById(Integer subcategoryId) {
        return this.subcategoryRepository
                .existsById(subcategoryId);
    }
}

