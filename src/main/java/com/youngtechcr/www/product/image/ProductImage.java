package com.youngtechcr.www.product.image;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.youngtechcr.www.product.Product;
import com.youngtechcr.www.domain.OneAmongMany;
import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.storage.Storable;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_product_image")
@JsonDeserialize(builder = ProductImage.Builder.class)
public class ProductImage implements Storable, Timestamped, OneAmongMany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product_image")
    private Integer productImageId;
    @Column(name = "server_name")
    private String serverName;
    @Column(name = "original_name")
    private String originalName;
    @Column(name = "relative_path")
    private String relativePath;
    @Column(name = "is_main_image")
    @JsonProperty("main")
    private boolean isMain;
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


   private ProductImage(Integer productImageId, String serverName, String originalName, String relativePath, boolean isMain, String mimeType, long sizeInBytes, LocalDateTime createdAt, LocalDateTime updatedAt, Product product) {
        this.productImageId = productImageId;
        this.serverName = serverName;
        this.originalName = originalName;
        this.relativePath = relativePath;
        this.isMain = isMain;
        this.mimeType = mimeType;
        this.sizeInBytes = sizeInBytes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.product = product;
    }

    public Integer getProductImageId() {
        return productImageId;
    }

    @Override
    @JsonIgnore
    public String getServerName() {
        return serverName;
    }

    @Override
    public String getOriginalName() {
        return originalName;
    }

    @Override
    @JsonIgnore
    public String getRelativePath() {
        return relativePath;
    }

    public boolean isMain() {
        return isMain;
    }

    @Override
    public void setIsMain(boolean main){
       this.isMain = main;
    }
    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public long getSizeInBytes() {
        return sizeInBytes;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Product getProduct() {
        return product;
    }

    public static Builder builder() {
       return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImage that = (ProductImage) o;
        return isMain == that.isMain && sizeInBytes == that.sizeInBytes && Objects.equals(productImageId, that.productImageId) && Objects.equals(serverName, that.serverName) && Objects.equals(originalName, that.originalName) && Objects.equals(relativePath, that.relativePath) && Objects.equals(mimeType, that.mimeType) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productImageId, serverName, originalName, relativePath, isMain, mimeType, sizeInBytes, createdAt, updatedAt, product);
    }

    @Override
    public String toString() {
        return "ProductImageFileData{" +
                "idProductImage=" + productImageId +
                ", serverFileName='" + serverName + '\'' +
                ", originalFileName='" + originalName + '\'' +
                ", relativePath='" + relativePath + '\'' +
                ", isMain=" + isMain +
                ", mimeType='" + mimeType + '\'' +
                ", sizeInBytes=" + sizeInBytes +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", product=" + product +
                '}';
    }

    @JsonPOJOBuilder
     static class Builder {
        private Integer productImageId;
        private String serverName;
        private String originalName;
        private String relativePath;
        private boolean isMain;
        private String mimeType;
        private long sizeInBytes;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Product product;

        public Builder withsetProductImageId(Integer productImageId) {
            this.productImageId = productImageId;
            return this;
        }

        public Builder withServerName(String serverName) {
            this.serverName = serverName;
            return this;
        }

        public Builder withOriginalName(String originalName) {
            this.originalName = originalName;
            return this;
        }

        public Builder withRelativePath(String relativePath) {
            this.relativePath = relativePath;
            return this;
        }

        public Builder withIsMain(boolean main) {
            isMain = main;
            return this;
        }

        public Builder withMimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        public Builder withSizeInBytes(long sizeInBytes) {
            this.sizeInBytes = sizeInBytes;
            return this;
        }

        public Builder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }
        @JsonBackReference("product-image")
        public Builder withProduct(Product product) {
            this.product = product;
            return this;
        }

        public ProductImage build() {
            return new ProductImage(
                    productImageId,
                    serverName,
                    originalName,
                    relativePath,
                    isMain,
                    mimeType,
                    sizeInBytes,
                    createdAt,
                    updatedAt,
                    product
            );
       }
    }
}

