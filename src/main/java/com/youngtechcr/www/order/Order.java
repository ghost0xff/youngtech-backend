package com.youngtechcr.www.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.youngtechcr.www.customer.Customer;
import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.order.item.OrderItem;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_order")
public class Order implements Timestamped {

    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private float total; // total = subtotal + iva
    private float subtotal;
    @Column(name = "iva_percentage")
    private float ivaPercentage;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_customer", referencedColumnName = "id_customer")
    private Customer customer;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonProperty("orderedProducts")
    private List<OrderItem> items;

    public Order() { }

    public Order(Integer id) {
                                   this.id = id;
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

    public float getIvaPercentage() { return ivaPercentage;}

    public void setIvaPercentage(float ivaPercentage) { this.ivaPercentage = ivaPercentage; }

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

    @JsonBackReference(value = "customer-order")
    public Customer getCustomer() {
                                        return this.customer;
                                                             }

    public void setCustomer(Customer customer) {
                                                     this.customer = customer;
                                                                              }

    @JsonManagedReference(value = "order-orderedproduct")
    public List<OrderItem> getItems() {
                                            return items;
                                                         }

    public void setItems(List<OrderItem> orderedProductsList) {
        this.items = orderedProductsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Float.compare(order.total, total) == 0 && Float.compare(order.subtotal, subtotal) == 0 && ivaPercentage == order.ivaPercentage && isDelivered == order.isDelivered && isCanceled == order.isCanceled && Objects.equals(id, order.id) && Objects.equals(orderDate, order.orderDate) && Objects.equals(deliveryDate, order.deliveryDate) && Objects.equals(createdAt, order.createdAt) && Objects.equals(updatedAt, order.updatedAt) && Objects.equals(customer, order.customer) && Objects.equals(items, order.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, total, subtotal, ivaPercentage, isDelivered, isCanceled, orderDate, deliveryDate, createdAt, updatedAt, customer, items);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id =" + id +
                ", total=" + total +
                ", subtotal=" + subtotal +
                ", ivaPercentage=" + ivaPercentage +
                ", isDelivered=" + isDelivered +
                ", isCanceled=" + isCanceled +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                    '}';
        }
}
