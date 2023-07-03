package com.youngtechcr.www.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.youngtechcr.www.domain.TimeStamped;
import com.youngtechcr.www.person.Person;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_order")
public class Order implements TimeStamped {

    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private float total; // total = subtotal + iva
    private float subtotal;
    @Column(name = "iva_percentage")
    private int ivaPercentage;
    @Column(name = "is_delivered")
    private boolean isDelivered;
    @Column(name = "is_canceled")
    private boolean isCanceled;
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "fk_id_person", referencedColumnName = "id_person")
    private Person person;
    @OneToMany(mappedBy = "order")
    @JsonProperty("orderedProducts")
    private List<OrderedProduct> orderedProductsList;

    public Order() { }

    public Order(Integer orderId) {
        this.orderId = orderId;
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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public int getIvaPercentage() {
        return ivaPercentage;
    }

    public void setIvaPercentage(int ivaPercentage) {
        this.ivaPercentage = ivaPercentage;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @JsonBackReference(value = "person-order")
    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @JsonManagedReference(value = "order-orderedproduct")
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
        Order order = (Order) o;
        return Float.compare(order.total, total) == 0 && Float.compare(order.subtotal, subtotal) == 0 && ivaPercentage == order.ivaPercentage && isDelivered == order.isDelivered && isCanceled == order.isCanceled && Objects.equals(orderId, order.orderId) && Objects.equals(orderDate, order.orderDate) && Objects.equals(deliveryDate, order.deliveryDate) && Objects.equals(createdAt, order.createdAt) && Objects.equals(updatedAt, order.updatedAt) && Objects.equals(person, order.person) && Objects.equals(orderedProductsList, order.orderedProductsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, total, subtotal, ivaPercentage, isDelivered, isCanceled, orderDate, deliveryDate, createdAt, updatedAt, person, orderedProductsList);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", total=" + total +
                ", subtotal=" + subtotal +
                ", ivaPercentage=" + ivaPercentage +
                ", isDelivered=" + isDelivered +
                ", isCanceled=" + isCanceled +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", person=" + person +
//                ", orderedProductsList=" + orderedProductsList +
                '}';
    }
}
