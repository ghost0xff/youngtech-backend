package com.youngtechcr.www.domain;

import com.youngtechcr.www.domain.interfaces.TimeStamped;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_ordered_product")
public class OrderedProducts implements TimeStamped {


    @Id
    @Column(name = "id_ordered_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderedProductsId;
    @Column(name = "is_delivered")
    private boolean isDelivered;
    @Column(name = "is_canceled")
    private boolean isCanceled;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "fk_id_order", referencedColumnName = "id_order")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "fk_id_product", referencedColumnName = "id_product")
    private Product product;

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

    public Integer getOrderedProductsId() {
        return orderedProductsId;
    }

    public void setOrderedProductsId(Integer orderedProductsId) {
        this.orderedProductsId = orderedProductsId;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedProducts that = (OrderedProducts) o;
        return isDelivered == that.isDelivered && isCanceled == that.isCanceled && Objects.equals(orderedProductsId, that.orderedProductsId) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(order, that.order) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderedProductsId, isDelivered, isCanceled, createdAt, updatedAt, order, product);
    }

    @Override
    public String toString() {
        return "OrderedProducts{" +
                "orderedProductsId=" + orderedProductsId +
                ", isDelivered=" + isDelivered +
                ", isCanceled=" + isCanceled +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", order=" + order +
                ", product=" + product +
                '}';
    }
}
