package com.youngtechcr.www.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_brand")
public class Brand {

    @Id
    @Column(name = "id_brand")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idBrand;
    private String name;

    public Brand() {
    }

    public Brand(Integer idBrand, String name) {
        this.idBrand = idBrand;
        this.name = name;
    }

    public Integer getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(Integer idBrand) {
        this.idBrand = idBrand;
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
        return Objects.equals(idBrand, brand.idBrand) && Objects.equals(name, brand.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBrand, name);
    }

    @Override
    public String toString() {
        return "Brand{" +
                "idBrand=" + idBrand +
                ", name='" + name + '\'' +
                '}';
    }
}
