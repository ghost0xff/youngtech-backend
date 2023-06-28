package com.youngtechcr.www.user;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.youngtechcr.www.role.Role;
import com.youngtechcr.www.sale.Sale;
import com.youngtechcr.www.domain.TimeStamped;
import com.youngtechcr.www.order.Order;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_user")
public class User implements TimeStamped {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String username;
    private String password;
    private String email;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "fk_id_role", referencedColumnName = "id_role")
    private Role role;
    @OneToMany(mappedBy = "user")
    @JsonProperty("orders")
    private List<Order> orderList;
    @OneToMany(mappedBy = "user")
    @JsonProperty("sales")
    private List<Sale> saleList;

    public User(){}

    public User(Integer userId) {
        this.userId = userId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonBackReference(value = "user-role")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @JsonManagedReference(value = "user-sale")
    public List<Sale> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<Sale> saleList) {
        this.saleList = saleList;
    }

    @JsonManagedReference(value = "user-order")
    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt) && Objects.equals(role, user.role) && Objects.equals(orderList, user.orderList) && Objects.equals(saleList, user.saleList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, email, createdAt, updatedAt, role, orderList, saleList);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", role=" + role +
//                ", orderList=" + orderList +
//                ", saleList=" + saleList +
                '}';
    }
}
