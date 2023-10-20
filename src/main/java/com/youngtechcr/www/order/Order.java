package com.youngtechcr.www.order;

import com.fasterxml.jackson.annotation.*;
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
    @Column(name = "total_discount_currency")
    private float totalDiscountCurrency;

    @Column(name = "iva_percentage")
    private float ivaPercentage;
    @Column(name = "is_delivered")
    private boolean isDelivered;
    @Column(name = "is_canceled")
    private boolean isCanceled;
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "delivery_date_from")
    private LocalDateTime deliveryFrom;
    @Column(name = "delivery_date_to")
    private LocalDateTime deliveryTo;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_customer", referencedColumnName = "id_customer")
    private Customer customer;
    @OneToMany(mappedBy = "order")
    @JsonProperty("orderedProducts")
    private List<OrderItem> items;

    public Order() { }

    public Order(Integer id) {
                                   this.id = id;
                                                }

    @Override
    @JsonIgnore
    public LocalDateTime getCreatedAt() { return this.createdAt; }

    @Override
    public void setCreatedAt(LocalDateTime timestamp) {
                                                            this.createdAt = timestamp;
                                                                                       }

    @Override
    @JsonIgnore
    public LocalDateTime getUpdatedAt() { return this.updatedAt; }

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

    public float getTotalDiscountCurrency() {
        return totalDiscountCurrency;
    }

    public void setTotalDiscountCurrency(float totalDiscountCurrency) {
        this.totalDiscountCurrency = totalDiscountCurrency;
    }

    @JsonBackReference(value = "customer-order")
    public Customer getCustomer() { return this.customer; }

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

    public LocalDateTime getDeliveryFrom() {
        return deliveryFrom;
    }

    public void setDeliveryFrom(LocalDateTime deliveryFrom) {
        this.deliveryFrom = deliveryFrom;
    }

    public LocalDateTime getDeliveryTo() {
        return deliveryTo;
    }

    public void setDeliveryTo(LocalDateTime deliveryTo) {
        this.deliveryTo = deliveryTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (Float.compare(total, order.total) != 0) return false;
        if (Float.compare(subtotal, order.subtotal) != 0) return false;
        if (Float.compare(totalDiscountCurrency, order.totalDiscountCurrency) != 0) return false;
        if (Float.compare(ivaPercentage, order.ivaPercentage) != 0) return false;
        if (isDelivered != order.isDelivered) return false;
        if (isCanceled != order.isCanceled) return false;
        if (!Objects.equals(id, order.id)) return false;
        if (!Objects.equals(orderDate, order.orderDate)) return false;
        if (!Objects.equals(createdAt, order.createdAt)) return false;
        if (!Objects.equals(updatedAt, order.updatedAt)) return false;
        if (!Objects.equals(deliveryFrom, order.deliveryFrom)) return false;
        if (!Objects.equals(deliveryTo, order.deliveryTo)) return false;
        if (!Objects.equals(customer, order.customer)) return false;
        return Objects.equals(items, order.items);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (total != 0.0f ? Float.floatToIntBits(total) : 0);
        result = 31 * result + (subtotal != 0.0f ? Float.floatToIntBits(subtotal) : 0);
        result = 31 * result + (totalDiscountCurrency != 0.0f ? Float.floatToIntBits(totalDiscountCurrency) : 0);
        result = 31 * result + (ivaPercentage != 0.0f ? Float.floatToIntBits(ivaPercentage) : 0);
        result = 31 * result + (isDelivered ? 1 : 0);
        result = 31 * result + (isCanceled ? 1 : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (deliveryFrom != null ? deliveryFrom.hashCode() : 0);
        result = 31 * result + (deliveryTo != null ? deliveryTo.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", total=" + total +
                ", subtotal=" + subtotal +
                ", totalDiscountCurrency=" + totalDiscountCurrency +
                ", ivaPercentage=" + ivaPercentage +
                ", isDelivered=" + isDelivered +
                ", isCanceled=" + isCanceled +
                ", orderDate=" + orderDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deliveryFrom=" + deliveryFrom +
                ", deliveryTo=" + deliveryTo +
                '}';
    }
}
