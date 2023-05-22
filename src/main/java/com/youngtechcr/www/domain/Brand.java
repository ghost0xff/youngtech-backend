package com.youngtechcr.www.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_brand")
public class Brand {

    @Id
    @Column(name = "id_brand")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer brandId;
    private String name;

    public Brand() {
    }

    public Brand(Integer brandId){
        this.brandId = brandId;
    }

    public Brand(Integer brandId, String name) {
        this.brandId = brandId;
        this.name = name;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(brandId, brand.brandId) && Objects.equals(name, brand.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandId, name);
    }

    @Override
    public String toString() {
        return "Brand{" +
                "idBrand=" + brandId +
                ", name='" + name + '\'' +
                '}';
    }
}
