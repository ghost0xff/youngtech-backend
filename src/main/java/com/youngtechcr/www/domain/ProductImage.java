package com.youngtechcr.www.domain;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product_image")
    private Integer idProductImage;

    @Column(name = "server_name")
    private String serverName;

    @Column(name = "original_name")
    private String originalName;

    private String path;

    public ProductImage() {
    }

    public ProductImage(Integer idProductImage) {
        this.idProductImage = idProductImage;
    }

    public ProductImage(Integer idProductImage, String serverName, String originalName, String path) {
        this.idProductImage = idProductImage;
        this.serverName = serverName;
        this.originalName = originalName;
        this.path = path;
    }

    public Integer getIdProductImage() {
        return idProductImage;
    }

    public void setIdProductImage(Integer idProductImage) {
        this.idProductImage = idProductImage;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
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
        return Objects.equals(idProductImage, that.idProductImage) && Objects.equals(serverName, that.serverName) && Objects.equals(originalName, that.originalName) && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProductImage, serverName, originalName, path);
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "idProductImage=" + idProductImage +
                ", serverName='" + serverName + '\'' +
                ", originalName='" + originalName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}

