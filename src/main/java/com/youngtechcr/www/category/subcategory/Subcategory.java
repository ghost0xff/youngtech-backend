package com.youngtechcr.www.category.subcategory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.youngtechcr.www.category.Category;
import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.product.Product;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_subcategory")
public class Subcategory implements Timestamped {

    @Id
    @Column(name = "id_subcategory")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "fk_id_category", referencedColumnName = "id_category")
    private Category category;
    private String name;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "subcategory", fetch = FetchType.LAZY)
    @JsonProperty("products")
    private List<Product> products;

    public Subcategory() {
    }

    public Subcategory(Integer id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime timestamp) {
        this.createdAt = timestamp;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime timestamp) {
        this.updatedAt = timestamp;
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

    @JsonBackReference("category-subcategory")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonManagedReference(value = "product-subcategory")
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subcategory that = (Subcategory) o;
        return Objects.equals(id, that.id) && Objects.equals(category, that.category) && Objects.equals(name, that.name) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, createdAt, updatedAt, products);
    }

    @Override
    public String toString() {
        return "Subcategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}