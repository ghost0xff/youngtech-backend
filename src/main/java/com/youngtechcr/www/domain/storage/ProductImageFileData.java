package com.youngtechcr.www.domain.storage;


import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_product_image")
public class ProductImageFileData implements FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product_image")
    private Integer idProductImage;

    @Column(name = "server_name")
    private String serverName;

    @Column(name = "original_name")
    private String originalName;

    private String path;

    @OneToMany(mappedBy = "productImageFileData")
    List<ProductAndProductImageRelationship> productList;

    public ProductImageFileData() {
    }

    public ProductImageFileData(Integer idProductImage) {
        this.idProductImage = idProductImage;
    }

    public ProductImageFileData(Integer idProductImage, String serverName, String originalName, String path) {
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

    @Override
    public String getServerFileName(){
        return this.serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public String getOriginalFileName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @Override
    public String getRelativeFilePath(){
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImageFileData that = (ProductImageFileData) o;
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

