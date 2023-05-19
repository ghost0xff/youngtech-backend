package com.youngtechcr.www.backend.domain;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product_image")
    private Integer idProductImage;

    private String name;
    private String path;

    public ProductImage() {
    }

    public ProductImage(Integer idProductImage) {
        this.idProductImage = idProductImage;
    }

    public ProductImage(Integer idProductImage, String name, String path) {
        this.idProductImage = idProductImage;
        this.name = name;
        this.path = path;
    }

    public Integer getIdProductImage() {
        return idProductImage;
    }

    public void setIdProductImage(Integer idProductImage) {
        this.idProductImage = idProductImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImage that = (ProductImage) o;
        return Objects.equals(idProductImage, that.idProductImage) && Objects.equals(name, that.name) && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProductImage, name, path);
    }


    @Override
    public String toString() {
        return "ProductImage{" +
                "idProductImage=" + idProductImage +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}

