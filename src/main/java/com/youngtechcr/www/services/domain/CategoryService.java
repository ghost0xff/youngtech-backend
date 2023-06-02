package com.youngtechcr.www.services.domain;

import com.youngtechcr.www.domain.Category;
import com.youngtechcr.www.domain.Subcategory;
import com.youngtechcr.www.exceptions.custom.AlreadyExistsException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.exceptions.custom.ValueMismatchException;
import com.youngtechcr.www.repositories.CategoryRepository;
import com.youngtechcr.www.utils.ErrorMessages;
import com.youngtechcr.www.utils.TimestampUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubcategoryService subcategoryService;

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    @Transactional(readOnly = true)
    public Category findCategoryById(Integer categoryId) {
        return this
                .categoryRepository
                    .findById(categoryId)
                        .orElseThrow( () ->  new NoDataFoundException(ErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND));
    }

    @Transactional
    public Category createCategory(Category categoryToBeCreated) {
        if (!this.categoryRepository.existsByName(categoryToBeCreated.getName())) {
            TimestampUtils.setTimestampsToNow(categoryToBeCreated);
            Category createdCategory = this.categoryRepository.save(categoryToBeCreated);
            log.info("Created new category: " + createdCategory);
            return createdCategory;
        }
        throw new AlreadyExistsException(ErrorMessages.CANT_CREATE_DUPLICATE_NAME);
    }

    @Transactional
    public Category updateCategoryById(Integer categoryId, Category categoryToBeUpdated) {
        if (categoryId.equals(categoryToBeUpdated.getCategoryId())) {
                LocalDateTime storedCreatedAtTimestamp = this.findCategoryById(categoryId).getCreatedAt();
            TimestampUtils.updateTimeStamps(categoryToBeUpdated, storedCreatedAtTimestamp);
            Category updatedCategory = this.categoryRepository.save(categoryToBeUpdated);
            log.info("Updated category: " + updatedCategory);
            return updatedCategory;
        }
        throw new ValueMismatchException(ErrorMessages.PROVIDED_IDS_DONT_MATCH);
    }

    @Transactional
    public void deleteCategoryById(Integer categoryId) {
        if(this.categoryRepository.existsById(categoryId)) {
            this.categoryRepository.deleteById(categoryId);
            log.info("Deleted category with id: " + categoryId);
            return;
        }
        throw new NoDataFoundException(ErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
    }

    @Transactional(readOnly = true)
    public List<Subcategory> findSubcategories(Integer categoryId) {
        Category requestedCategory = this
                .categoryRepository
                .findById(categoryId)
                .orElseThrow( () -> new NoDataFoundException(ErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND));
        return requestedCategory.getSubcategoryList();
    }


    @Transactional
    public Subcategory createSubcategoryByCategoryId(Integer categoryId, Subcategory subcategoryToBeCreated) {
        Category categoryToBeAddedNewSubcategory = this.findCategoryById(categoryId);
        subcategoryToBeCreated.setCategory(categoryToBeAddedNewSubcategory);
        Subcategory createdSubcategory = this.subcategoryService.create(subcategoryToBeCreated);
        log.info("Created subcategory: " + createdSubcategory + " in category: " + categoryToBeAddedNewSubcategory);
        return createdSubcategory;
    }

    @Transactional(readOnly = true)
    public Subcategory findSubcategoryByCategoryId(Integer categoryId, Integer subcategoryId) {
        Category categoryToBeConsulted = this.findCategoryById(categoryId);
        Subcategory requetedSubcategory = this.subcategoryService.findById(subcategoryId);
        if(requetedSubcategory.getCategory() != null && requetedSubcategory.getCategory().equals(categoryToBeConsulted)) {
            return requetedSubcategory;
        }
        throw new ValueMismatchException(ErrorMessages.REQUESTED_CHILD_ELEMENT_DOESNT_EXIST);
    }

    @Transactional
    public Subcategory updateSubcategoryByCategoryId(Integer categoryId, Integer subcategoryId, Subcategory subcategoryToBeUpdated) {
        Category categoryWhosSubcategoryWillBeModified = this.findCategoryById(categoryId);
        if(subcategoryToBeUpdated.getCategory().getCategoryId().equals(categoryWhosSubcategoryWillBeModified.getCategoryId())) {
            var updatedSubcategory = subcategoryService.updateById(subcategoryId, subcategoryToBeUpdated);
            log.info("Updated subcategory: " + updatedSubcategory + "in category: " + categoryWhosSubcategoryWillBeModified);
            return updatedSubcategory;
        }
        throw new ValueMismatchException(ErrorMessages.REQUESTED_CHILD_ELEMENT_DOESNT_EXIST);
    }

    @Transactional
    public void deleteSubcategoryByCategoryId(Integer categoryId, Integer subcategoryId) {
        Category categoryWhosSubcategotyWillBeDeleted = this.findCategoryById(categoryId);
        Subcategory subcategoryToBeDeleted = this.subcategoryService.findById(subcategoryId);
        if(subcategoryToBeDeleted.getCategory().getCategoryId().equals(categoryWhosSubcategotyWillBeDeleted.getCategoryId())) {
            this.subcategoryService.deleteById(subcategoryId);
            log.info("Deleted subcategory: " + subcategoryToBeDeleted + " from category " + categoryWhosSubcategotyWillBeDeleted);
            return;
        }
        throw new ValueMismatchException(ErrorMessages.REQUESTED_CHILD_ELEMENT_DOESNT_EXIST);
    }
}
