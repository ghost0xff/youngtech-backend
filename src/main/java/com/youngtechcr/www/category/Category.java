package com.youngtechcr.www.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.youngtechcr.www.category.subcategory.Subcategory;
import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.product.Product;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_category")
public class Category implements Timestamped {

    @Id
    @Column(name = "id_category")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "category")
    @JsonProperty("products")
    List<Product> products;
    @OneToMany(mappedBy = "category")
    @JsonProperty("subcategories")
    List<Subcategory> subcategories;

    public Category() {}

    public Category(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonManagedReference(value = "product-category")
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @JsonBackReference(value = "category-subcategory")
    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(createdAt, category.createdAt) && Objects.equals(updatedAt, category.updatedAt) && Objects.equals(products, category.products) && Objects.equals(subcategories, category.subcategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdAt, updatedAt, products, subcategories);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}