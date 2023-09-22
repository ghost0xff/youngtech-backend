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
    private Integer id;
    @Column(name = "server_name")
    private String serverName;
    @Column(name = "original_name")
    private String originalName;
    @Column(name = "relative_path")
    private String relativePath;
    @Column(name = "is_main_image")
    @JsonProperty("main")
    private boolean main;
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

    public ProductImage() {}

   private ProductImage(Integer id, String serverName, String originalName, String relativePath, boolean main, String mimeType, long sizeInBytes, LocalDateTime createdAt, LocalDateTime updatedAt, Product product) {
        this.id = id;
        this.serverName = serverName;
        this.originalName = originalName;
        this.relativePath = relativePath;
        this.main = main;
        this.mimeType = mimeType;
        this.sizeInBytes = sizeInBytes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.product = product;
    }

    public Integer getId() {
        return id;
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

    public boolean main() {
        return main;
    }
    public void main(boolean main) {
        this.main = main;
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

    @JsonIgnore
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
        return main == that.main && sizeInBytes == that.sizeInBytes && Objects.equals(id, that.id) && Objects.equals(serverName, that.serverName) && Objects.equals(originalName, that.originalName) && Objects.equals(relativePath, that.relativePath) && Objects.equals(mimeType, that.mimeType) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serverName, originalName, relativePath, main, mimeType, sizeInBytes, createdAt, updatedAt, product);
    }

    @Override
    public String toString() {
        return "ProductImageFileData{" +
                "idProductImage=" + id +
                ", serverFileName='" + serverName + '\'' +
                ", originalFileName='" + originalName + '\'' +
                ", relativePath='" + relativePath + '\'' +
                ", main=" + main +
                ", mimeType='" + mimeType + '\'' +
                ", sizeInBytes=" + sizeInBytes +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", product=" + product +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "")
     static class Builder {
        private Integer id;
        private String serverName;
        private String originalName;
        private String relativePath;
        private boolean isMain;
        private String mimeType;
        private long sizeInBytes;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Product product;

        public Builder withsetProductImageId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder serverName(String serverName) {
            this.serverName = serverName;
            return this;
        }

        public Builder originalName(String originalName) {
            this.originalName = originalName;
            return this;
        }

        public Builder relativePath(String relativePath) {
            this.relativePath = relativePath;
            return this;
        }

        public Builder main(boolean main) {
            isMain = main;
            return this;
        }

        public Builder mimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        public Builder sizeInBytes(long sizeInBytes) {
            this.sizeInBytes = sizeInBytes;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }
        @JsonBackReference("product-image")
        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public ProductImage build() {
            return new ProductImage(
                    id,
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

