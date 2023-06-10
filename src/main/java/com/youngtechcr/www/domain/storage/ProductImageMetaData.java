package com.youngtechcr.www.domain.storage;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.OneAmongMany;
import com.youngtechcr.www.domain.TimeStamped;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_product_image")
public class ProductImageMetaData implements FileMetaData, TimeStamped, OneAmongMany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product_image")
    private Integer productImageId;
    @Column(name = "server_name")
    private String serverFileName;
    @Column(name = "original_name")
    private String originalFileName;
    @Column(name = "relative_path")
    private String relativePath;
    @Column(name = "is_main_image")
    @JsonProperty("main")
    private boolean isMainImage;
    @Column(name = "mime_type")
    private String mimeType;
    @Column(name = "size_in_bytes")
    private long sizeInBytes;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "fk_id_product", referencedColumnName = "id_product")
    private Product product;


    public ProductImageMetaData() {
    }

    public ProductImageMetaData(String serverFileName, String originalFileName, String relativePath) {
        this.serverFileName = serverFileName;
        this.originalFileName = originalFileName;
        this.relativePath = relativePath;
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
    @JsonIgnore
    public String getOriginalFileName() {
        return this.originalFileName;
    }

    @Override
    @JsonProperty
    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    @Override
    @JsonIgnore
    public String getServerFileName() {
        return this.serverFileName;
    }

    @Override
    @JsonProperty
    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    @Override
    @JsonIgnore
    public String getRelativePath() {
        return this.relativePath;
    }

    @Override
    @JsonProperty
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    @Override
    public String getMimeType() {
        return this.mimeType;
    }

    @Override
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public long getSizeInBytes() {
        return this.sizeInBytes;
    }

    @Override
    public void setSizeInBytes(long sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }

    public Integer getProductImageId() {
        return productImageId;
    }

    public void setProductImageId(Integer productImageId) {
        this.productImageId = productImageId;
    }


    public boolean isMain() {
        return isMainImage;
    }

    public void setMain(boolean mainImage) {
        isMainImage = mainImage;
    }

    @JsonBackReference(value = "product-image")
    public Product getProduct() {
        return product;
    }

    @JsonProperty
    public void setProduct(Product product) {
        this.product = product;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImageMetaData that = (ProductImageMetaData) o;
        return isMainImage == that.isMainImage && sizeInBytes == that.sizeInBytes && Objects.equals(productImageId, that.productImageId) && Objects.equals(serverFileName, that.serverFileName) && Objects.equals(originalFileName, that.originalFileName) && Objects.equals(relativePath, that.relativePath) && Objects.equals(mimeType, that.mimeType) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productImageId, serverFileName, originalFileName, relativePath, isMainImage, mimeType, sizeInBytes, createdAt, updatedAt, product);
    }

    @Override
    public String toString() {
        return "ProductImageFileData{" +
                "idProductImage=" + productImageId +
                ", serverFileName='" + serverFileName + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", relativePath='" + relativePath + '\'' +
                ", isMainImage=" + isMainImage +
                ", mimeType='" + mimeType + '\'' +
                ", sizeInBytes=" + sizeInBytes +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", product=" + product +
                '}';
    }
}

