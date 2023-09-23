package com.youngtechcr.www.customer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.order.Order;
import com.youngtechcr.www.person.Person;
import com.youngtechcr.www.sale.Sale;
import com.youngtechcr.www.security.user.User;
import com.youngtechcr.www.shoppingcart.ShoppingCart;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_customer")
public class Customer implements Timestamped {

    @Id
    @Column(name = "id_customer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "fk_id_user",
            referencedColumnName = "id_user")
    private User user;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Sale> sales;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ShoppingCart shoppingCart;


    @JsonManagedReference(value = "customer-sale")
    public List<Sale> getSales() {
        return sales;
    }

    @JsonManagedReference(value = "customer-order")
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    @JsonManagedReference(value = "customer-cart")
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(user, customer.user) && Objects.equals(createdAt, customer.createdAt) && Objects.equals(updatedAt, customer.updatedAt) && Objects.equals(orders, customer.orders) && Objects.equals(sales, customer.sales) && Objects.equals(shoppingCart, customer.shoppingCart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, createdAt, updatedAt, orders, sales, shoppingCart);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
