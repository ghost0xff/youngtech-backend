package com.youngtechcr.www.controllers;

import com.youngtechcr.www.domain.Category;
import com.youngtechcr.www.domain.Subcategory;
import com.youngtechcr.www.services.CategoryService;
import com.youngtechcr.www.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/categories")
public class CategoryAndSubcategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable("id")Integer categorId) {
        Category fetchedCategory = this.categoryService.findCategoryById(categorId);
        return ResponseEntity.ok().body(fetchedCategory);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category categoryToBeCreated) {
        Category createdCategory = this.categoryService.createCategory(categoryToBeCreated);
        return ResponseEntityUtils.created(categoryToBeCreated);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Category> updateCategoryById(
            @PathVariable("id") Integer categoryId,
            @RequestBody Category categoryToBeUpdated
    ) {
        Category updatedCategory = this.categoryService.updateCategoryById(categoryId, categoryToBeUpdated);
        return ResponseEntity.ok().body(updatedCategory);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") Integer categoryId) {
        this.categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}/subcategories")
    public ResponseEntity<List<Subcategory>> findSubcategories(@PathVariable("id") Integer categoryId) {
        List<Subcategory> subcategoryList = this.categoryService.findSubcategories(categoryId);
        return ResponseEntity.ok().body(subcategoryList);
    }

    @PostMapping(path = "/{id}/subcategories")
    public ResponseEntity<Subcategory> createSubcategory(
            @PathVariable("id") Integer categoryId,
            @RequestBody Subcategory subcategoryToBeCreated
    ) {
        Subcategory createdSubcategory = this.categoryService.createSubcategoryByCategoryId(categoryId, subcategoryToBeCreated);
        return ResponseEntityUtils.created(createdSubcategory);
    }

    @PutMapping(path = "/{categoryId}/subcategories/{subcategoryId}")
    public ResponseEntity<Subcategory> updateSubcategoryByCategory(
            @PathVariable Integer categoryId,
            @PathVariable Integer subcategoryId,
            @RequestBody Subcategory subcategoryToBeUpdated
    ){
        Subcategory updatedSubcategory = this.categoryService.updateSubcategoryByCategoryId(categoryId, subcategoryId, subcategoryToBeUpdated);
        return ResponseEntity.ok().body(updatedSubcategory);
    }

    @GetMapping(path = "/{categoryId}/subcategories/{subcategoryId}")
    public ResponseEntity<Subcategory> findSubCategoryByCategory(
            @PathVariable Integer categoryId,
            @PathVariable Integer subcategoryId
    ){
        Subcategory fetchedSubcategory = this.categoryService.findSubcategoryByCategoryId(categoryId, subcategoryId);
        return ResponseEntity.ok().body(fetchedSubcategory);
    }

    @DeleteMapping(path = "/{categoryId}/subcategories/{subcategoryId}")
    public ResponseEntity<Subcategory> deleteSubCategoryByCategory(
            @PathVariable Integer categoryId,
            @PathVariable Integer subcategoryId
    ) {
        this.categoryService.deleteSubcategoryByCategoryId(categoryId, subcategoryId);
        return ResponseEntity.noContent().build();
    }

}
