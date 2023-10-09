package com.youngtechcr.www.order.item;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.order.Order;
import com.youngtechcr.www.product.Product;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_order_item")
public class OrderItem implements Timestamped {


    @Id
    @Column(name = "id_order_item")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "is_delivered")
    private boolean isDelivered;
    @Column(name = "is_canceled")
    private boolean isCanceled;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    private int quantity;
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

    public void setCreatedAt(LocalDateTime timestamp) {
        this.createdAt = timestamp;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime timestamp) {
        this.updatedAt = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @JsonBackReference(value = "order-orderedproduct")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @JsonManagedReference(value = "product-ordered_products")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

        OrderItem item = (OrderItem) o;

        if (isDelivered != item.isDelivered) return false;
        if (isCanceled != item.isCanceled) return false;
        if (quantity != item.quantity) return false;
        if (!Objects.equals(id, item.id)) return false;
        if (!Objects.equals(createdAt, item.createdAt)) return false;
        if (!Objects.equals(updatedAt, item.updatedAt)) return false;
        if (!Objects.equals(order, item.order)) return false;
        return Objects.equals(product, item.product);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (isDelivered ? 1 : 0);
        result = 31 * result + (isCanceled ? 1 : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + quantity;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderedProduct{" +
                "orderedProductsId=" + id +
                ", isDelivered=" + isDelivered +
                ", isCanceled=" + isCanceled +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", quantity=" + quantity +
                '}';
    }
}
