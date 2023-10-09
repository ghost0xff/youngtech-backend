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

        if (stock != product.stock) return false;
        if (Float.compare(price, product.price) != 0) return false;
        if (Float.compare(discountPercentage, product.discountPercentage) != 0) return false;
        if (!Objects.equals(id, product.id)) return false;
        if (!Objects.equals(name, product.name)) return false;
        if (!Objects.equals(description, product.description)) return false;
        if (!Objects.equals(createdAt, product.createdAt)) return false;
        if (!Objects.equals(updatedAt, product.updatedAt)) return false;
        if (!Objects.equals(brand, product.brand)) return false;
        if (!Objects.equals(category, product.category)) return false;
        if (!Objects.equals(subcategory, product.subcategory)) return false;
        if (!Objects.equals(images, product.images)) return false;
        if (!Objects.equals(shoppingCartItems, product.shoppingCartItems))
            return false;
        return Objects.equals(orderedProductsList, product.orderedProductsList);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + stock;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != 0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (discountPercentage != 0.0f ? Float.floatToIntBits(discountPercentage) : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (subcategory != null ? subcategory.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (shoppingCartItems != null ? shoppingCartItems.hashCode() : 0);
        result = 31 * result + (orderedProductsList != null ? orderedProductsList.hashCode() : 0);
        return result;
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
