package com.youngtechcr.www.domain;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_order")
public class Order {

    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idOrder;
    @OneToMany(mappedBy = "order")
    private List<OrderAndSaleRelationship> sales;

    private float total;
    private float subtotal;
    private int iva;
    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    private boolean delivered;

    public Order() {
    }

    public Order(Integer idInvoice) {
        this.idOrder = idInvoice;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
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

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<OrderAndSaleRelationship> getSales() {
        return sales;
    }

    public void setSales(List<OrderAndSaleRelationship> sales) {
        this.sales = sales;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Float.compare(order.total, total) == 0 && Float.compare(order.subtotal, subtotal) == 0 && iva == order.iva && delivered == order.delivered && Objects.equals(idOrder, order.idOrder) && Objects.equals(sales, order.sales) && Objects.equals(orderDate, order.orderDate) && Objects.equals(deliveryDate, order.deliveryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, sales, total, subtotal, iva, orderDate, deliveryDate, delivered);
    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrder=" + idOrder +
                ", total=" + total +
                ", subtotal=" + subtotal +
                ", iva=" + iva +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", delivered=" + delivered +
                '}';
    }
}
