package com.youngtechcr.www.subcategory;

import com.youngtechcr.www.exceptions.custom.AlreadyExistsException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.exceptions.custom.ValueMismatchException;
import com.youngtechcr.www.http.BasicCrudService;
import com.youngtechcr.www.exceptions.ErrorMessages;
import com.youngtechcr.www.domain.TimestampedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SubcategoryService implements BasicCrudService<Subcategory> {

    @Autowired
    private SubcategoryRepository subcategoryRepository;
    private static final Logger log = LoggerFactory.getLogger(SubcategoryService.class);

    @Override
    public Subcategory findById(Integer subcategoryId) {
        return this
                .subcategoryRepository
                .findById(subcategoryId)
                .orElseThrow( () -> new NoDataFoundException(ErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND));
    }

    @Override
    public Subcategory create(Subcategory subcategoryToBeCreated) {
        if(!this.subcategoryRepository.existsByName(subcategoryToBeCreated.getName())) {
            TimestampedUtils.setTimestampsToNow(subcategoryToBeCreated);
            Subcategory createdSubcategory = this.subcategoryRepository.save(subcategoryToBeCreated);
            return createdSubcategory;
        }
        throw new AlreadyExistsException(ErrorMessages.CANT_CREATE_DUPLICATE_NAME);
    }

    @Override
    public Subcategory updateById(Integer subcategoryId, Subcategory subcategoryToBeUpdated) {
        if(subcategoryId.equals(subcategoryToBeUpdated.getSubcategoryId())){
            LocalDateTime storedCreatedAtTimestamp = this.findById(subcategoryId).getCreatedAt();
            TimestampedUtils.updateTimeStamps(subcategoryToBeUpdated, storedCreatedAtTimestamp);
            var updatedSubcategory = this.subcategoryRepository.save(subcategoryToBeUpdated);
            return updatedSubcategory;
        }
        throw new ValueMismatchException(ErrorMessages.PROVIDED_IDS_DONT_MATCH);
    }

    @Override
    public void deleteById(Integer categoryId) {
        if(this.subcategoryRepository.existsById(categoryId)) {
            this.subcategoryRepository.deleteById(categoryId);
            return;
        }
        throw new NoDataFoundException(ErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
    }


}

