package com.youngtechcr.www.brand;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.youngtechcr.www.product.Product;
import com.youngtechcr.www.domain.Timestamped;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_brand")
public class Brand implements Timestamped {

    @Id
    @Column(name = "id_brand")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    @JsonProperty("products")
    private List<Product> products;

    public Brand() {
    }
    public Brand(Integer id){
        this.id = id;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime timestamp) {
        this.createdAt = timestamp;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
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

    @JsonManagedReference(value = "product-brand")
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
        Brand brand = (Brand) o;
        return Objects.equals(id, brand.id) && Objects.equals(name, brand.name) && Objects.equals(createdAt, brand.createdAt) && Objects.equals(updatedAt, brand.updatedAt) && Objects.equals(products, brand.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdAt, updatedAt, products);
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
