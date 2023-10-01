package com.youngtechcr.www.product;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.youngtechcr.www.brand.Brand;
import com.youngtechcr.www.category.Category;
import com.youngtechcr.www.domain.*;
import com.youngtechcr.www.order.item.OrderItem;
import com.youngtechcr.www.product.image.ProductImage;
import com.youngtechcr.www.sale.Sale;
import com.youngtechcr.www.category.subcategory.Subcategory;
import com.youngtechcr.www.shoppingcart.item.ShoppingCartItem;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_product")
public class Product implements Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Integer id;
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
    @JsonIgnore
    private Brand brand;
    @ManyToOne
    @JoinColumn(name = "fk_id_category", referencedColumnName = "id_category")
    @JsonIgnore
    private Category category;
    @ManyToOne
    @JoinColumn(name = "fk_id_subcategory", referencedColumnName = "id_subcategory")
    private Subcategory subcategory;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonProperty("images")
    private List<ProductImage> images;
    @OneToMany(mappedBy = "product")
    @JsonProperty("sales")
    @JsonIgnore
    private List<Sale> saleList;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ShoppingCartItem> shoppingCartItems;

    // TODO: IS THIS OBJECT NECESARY OR IS IT ONLY EATING MEMORY???
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderItem> orderedProductsList;


    public Product() {
    }

    public Product(Integer productId) {
        this.id = productId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public List<ProductImage> getImages() {
        return images;
    }
    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    @JsonBackReference(value = "product-sale")
    public List<Sale> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<Sale> saleList) {
        this.saleList = saleList;
    }

    @JsonBackReference(value = "product-ordered_products")
    public List<OrderItem> getOrderedProductsList() {
        return orderedProductsList;
    }
    public void setOrderedProductsList(List<OrderItem> orderedProductsList) {
        this.orderedProductsList = orderedProductsList;
    }

    @JsonBackReference(value = "product-cart_item")
    public List<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return stock == product.stock && Float.compare(price, product.price) == 0 && Float.compare(discountPercentage, product.discountPercentage) == 0 && Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(createdAt, product.createdAt) && Objects.equals(updatedAt, product.updatedAt) && Objects.equals(brand, product.brand) && Objects.equals(category, product.category) && Objects.equals(subcategory, product.subcategory) && Objects.equals(images, product.images) && Objects.equals(saleList, product.saleList) && Objects.equals(shoppingCartItems, product.shoppingCartItems) && Objects.equals(orderedProductsList, product.orderedProductsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stock, description, price, discountPercentage, createdAt, updatedAt, brand, category, subcategory, images, saleList, shoppingCartItems, orderedProductsList);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discountPercentage=" + discountPercentage +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
