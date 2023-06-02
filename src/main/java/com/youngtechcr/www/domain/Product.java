package com.youngtechcr.www.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.youngtechcr.www.domain.interfaces.TimeStamped;
import com.youngtechcr.www.domain.storage.ProductImageFileData;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_product")
public class Product implements TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Integer productId;
    private String name;
    private int stock;
    private String description;
    private float price;
    @Column(name = "discount_percentage")
    private float discountPercentage;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "fk_id_brand", referencedColumnName = "id_brand")
    private Brand brand;
    @ManyToOne
    @JoinColumn(name = "fk_id_category", referencedColumnName = "id_category")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "fk_id_subcategory", referencedColumnName = "id_subcategory")
    private Subcategory subcategory;
    @OneToMany(mappedBy = "product")
    @JsonProperty("images")
    private List<ProductImageFileData> productImageFileDataList;
    @OneToMany(mappedBy = "product")
    @JsonProperty("sales")
    private List<Sale> saleList;

    @OneToMany(mappedBy = "product")
    @JsonProperty("orderedProducts")
    private List<OrderedProduct> orderedProductsList;

    public Product() {
    }

    public Product(Integer productId) {
        this.productId = productId;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @JsonBackReference(value = "product-brand")
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @JsonBackReference(value = "product-category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonBackReference(value = "product-subcategory")
    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    @JsonManagedReference(value = "product-image")
    public List<ProductImageFileData> getProductImageFileDataList() {
        return productImageFileDataList;
    }
    public void setProductImageFileDataList(List<ProductImageFileData> productImageFileDataList) {
        this.productImageFileDataList = productImageFileDataList;
    }

    @JsonManagedReference(value = "product-sale")
    public List<Sale> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<Sale> saleList) {
        this.saleList = saleList;
    }

    @JsonManagedReference(value = "product-ordered_products")
    public List<OrderedProduct> getOrderedProductsList() {
        return orderedProductsList;
    }
    public void setOrderedProductsList(List<OrderedProduct> orderedProductsList) {
        this.orderedProductsList = orderedProductsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return stock == product.stock && Float.compare(product.price, price) == 0 && Float.compare(product.discountPercentage, discountPercentage) == 0 && Objects.equals(productId, product.productId) && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(createdAt, product.createdAt) && Objects.equals(updatedAt, product.updatedAt) && Objects.equals(brand, product.brand) && Objects.equals(category, product.category) && Objects.equals(subcategory, product.subcategory) && Objects.equals(productImageFileDataList, product.productImageFileDataList) && Objects.equals(saleList, product.saleList) && Objects.equals(orderedProductsList, product.orderedProductsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, stock, description, price, discountPercentage, createdAt, updatedAt, brand, category, subcategory, productImageFileDataList, saleList, orderedProductsList);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discountPercentage=" + discountPercentage +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", brand=" + brand +
                ", category=" + category +
                ", subcategory=" + subcategory +
//                ", productImageFileDataList=" + productImageFileDataList +
//                ", saleList=" + saleList +
//                ", orderedProductsList=" + orderedProductsList +
                '}';
    }
}