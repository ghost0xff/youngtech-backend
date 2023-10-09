package com.youngtechcr.www.shoppingcart.item;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.product.Product;
import com.youngtechcr.www.shoppingcart.ShoppingCart;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_shopping_cart_item")
public class ShoppingCartItem implements Timestamped {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_shopping_cart_item")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "fk_id_product",
            referencedColumnName = "id_product")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "fk_id_shopping_cart",
            referencedColumnName = "id_shopping_cart")
    private ShoppingCart shoppingCart;

    private int quantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ShoppingCartItem() {}

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    @Override
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }
    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonManagedReference(value = "product-cart_item")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @JsonBackReference(value = "cart-item")
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingCartItem that = (ShoppingCartItem) o;

        if (quantity != that.quantity) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(product, that.product)) return false;
        if (!Objects.equals(shoppingCart, that.shoppingCart)) return false;
        if (!Objects.equals(createdAt, that.createdAt)) return false;
        return Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (shoppingCart != null ? shoppingCart.hashCode() : 0);
        result = 31 * result + quantity;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShoppingCartItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

