package com.youngtechcr.www.domain;

import com.youngtechcr.www.domain.interfaces.TimeStamped;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_brand")
public class Brand implements TimeStamped {

    @Id
    @Column(name = "id_brand")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer brandId;
    private String name;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "brand")
    List<Product> productList;

    public Brand() {
    }
    public Brand(Integer brandId){
        this.brandId = brandId;
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
}
