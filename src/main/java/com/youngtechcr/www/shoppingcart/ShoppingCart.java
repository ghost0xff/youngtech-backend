package com.youngtechcr.www.shoppingcart;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.youngtechcr.www.customer.Customer;
import com.youngtechcr.www.domain.Timestamped;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_shopping_cart")
public class ShoppingCart implements Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_shopping_cart")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "fk_id_customer",
            referencedColumnName = "id_customer")
    private Customer customer;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "shoppingCart")
    private List<ShoppingCartItem> items;
    public ShoppingCart() {
    }

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
    @JsonBackReference(value = "customer-cart")
    public Customer getCustomer() {
        return this.customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @JsonManagedReference(value = "cart-item")
    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(id, that.id) && Objects.equals(customer, that.customer) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(items, that.items);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, customer, createdAt, updatedAt, items);
    }
    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
//                ", customer=" + customer +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
//                ", items=" + items +
                '}';
    }
}