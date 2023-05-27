package com.youngtechcr.www.domain.storage;


import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.interfaces.TimeStamped;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_product_image")
public class ProductImageFileData implements FileData, TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product_image")
    private Integer idProductImage;
    @Column(name = "server_name")
    private String serverFileName;
    @Column(name = "original_name")
    private String originalFileName;
    @Column(name = "relative_path")
    private String relativePath;
    @Column(name = "is_main_image")
    private boolean isMainImage;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "fk_id_product", referencedColumnName = "id_product")
    private Product product;

    public ProductImageFileData() {
    }

    public ProductImageFileData(Integer idProductImage) {
        this.idProductImage = idProductImage;
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

    @Override
    public String getOriginalFileName() {
        return this.originalFileName;
    }

    @Override
    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    @Override
    public String getServerFileName() {
        return this.serverFileName;
    }

    @Override
    public void getServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    @Override
    public String getRelativePath() {
        return this.relativePath;
    }

    @Override
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public Integer getIdProductImage() {
        return idProductImage;
    }

    public void setIdProductImage(Integer idProductImage) {
        this.idProductImage = idProductImage;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public boolean isMainImage() {
        return isMainImage;
    }

    public void setMainImage(boolean mainImage) {
        isMainImage = mainImage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImageFileData that = (ProductImageFileData) o;
        return isMainImage == that.isMainImage && Objects.equals(idProductImage, that.idProductImage) && Objects.equals(serverFileName, that.serverFileName) && Objects.equals(originalFileName, that.originalFileName) && Objects.equals(relativePath, that.relativePath) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProductImage, serverFileName, originalFileName, relativePath, isMainImage, createdAt, updatedAt, product);
    }

    @Override
    public String toString() {
        return "ProductImageFileData{" +
                "idProductImage=" + idProductImage +
                ", serverFileName='" + serverFileName + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", relativePath='" + relativePath + '\'' +
                ", isMainImage=" + isMainImage +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", product=" + product +
                '}';
    }
}

