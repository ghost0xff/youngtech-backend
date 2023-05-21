package com.youngtechcr.www.domain;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_order")
public class Order {

    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idOrder;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sale", referencedColumnName = "id_sale")
    private Sale sale;

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

    public Order(Integer idInvoice, float total, float subtotal, int iva, Date orderDate, Date deliveryDate, Sale sale) {
        this.idOrder = idInvoice;
        this.total = total;
        this.subtotal = subtotal;
        this.iva = iva;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.sale = sale;
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

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order invoice = (Order) o;
        return Float.compare(invoice.total, total) == 0 && Float.compare(invoice.subtotal, subtotal) == 0 && iva == invoice.iva && Objects.equals(idOrder, invoice.idOrder) && Objects.equals(orderDate, invoice.orderDate) && Objects.equals(deliveryDate, invoice.deliveryDate) && Objects.equals(sale, invoice.sale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, total, subtotal, iva, orderDate, deliveryDate, sale);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "idInvoice=" + idOrder +
                ", total=" + total +
                ", subtotal=" + subtotal +
                ", iva=" + iva +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", sale=" + sale +
                '}';
    }
}
