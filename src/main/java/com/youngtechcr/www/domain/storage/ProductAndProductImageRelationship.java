package com.youngtechcr.www.domain.storage;

import com.youngtechcr.www.domain.Product;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_product_and_product_image")
public class ProductAndProductImageRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product_and_product_image")
    private Integer productAndProductImageRelationshipId;

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id_product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_product_image", referencedColumnName = "id_product_image")
    private ProductImageFileData productImageFileData;

    public Integer getProductAndProductImageRelationshipId() {
        return productAndProductImageRelationshipId;
    }

    public void setProductAndProductImageRelationshipId(Integer productAndProductImageRelationshipId) {
        this.productAndProductImageRelationshipId = productAndProductImageRelationshipId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductImageFileData getProductImageFileData() {
        return productImageFileData;
    }

    public void setProductImageFileData(ProductImageFileData productImageFileData) {
        this.productImageFileData = productImageFileData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAndProductImageRelationship that = (ProductAndProductImageRelationship) o;
        return Objects.equals(productAndProductImageRelationshipId, that.productAndProductImageRelationshipId) && Objects.equals(product, that.product) && Objects.equals(productImageFileData, that.productImageFileData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productAndProductImageRelationshipId, product, productImageFileData);
    }

    @Override
    public String toString() {
        return "ProductAndProductImageRelationship{" +
                "productAndProductImageRelationshipId=" + productAndProductImageRelationshipId +
                ", product=" + product +
                ", productImageFileData=" + productImageFileData +
                '}';
    }
}
