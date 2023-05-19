package com.youngtechcr.www.domain;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_invoice")
public class Invoice {

    @Id
    @Column(name = "id_invoice")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInvoice;
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


    public Invoice() {
    }

    public Invoice(Integer idInvoice) {
        this.idInvoice = idInvoice;
    }

    public Invoice(Integer idInvoice, float total, float subtotal, int iva, Date orderDate, Date deliveryDate, Sale sale) {
        this.idInvoice = idInvoice;
        this.total = total;
        this.subtotal = subtotal;
        this.iva = iva;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.sale = sale;
    }

    public Integer getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(Integer idInvoice) {
        this.idInvoice = idInvoice;
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
        Invoice invoice = (Invoice) o;
        return Float.compare(invoice.total, total) == 0 && Float.compare(invoice.subtotal, subtotal) == 0 && iva == invoice.iva && Objects.equals(idInvoice, invoice.idInvoice) && Objects.equals(orderDate, invoice.orderDate) && Objects.equals(deliveryDate, invoice.deliveryDate) && Objects.equals(sale, invoice.sale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInvoice, total, subtotal, iva, orderDate, deliveryDate, sale);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "idInvoice=" + idInvoice +
                ", total=" + total +
                ", subtotal=" + subtotal +
                ", iva=" + iva +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", sale=" + sale +
                '}';
    }
}
