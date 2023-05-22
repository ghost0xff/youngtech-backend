package com.youngtechcr.www.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_order_and_sale")
public class OrderAndSaleRelationship {

    @Id
    @Column(name = "id_order_and_sale")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderSaleRelationshipId;

    @ManyToOne
    @JoinColumn(name = "id_order", referencedColumnName = "id_order")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "id_sale", referencedColumnName = "id_sale")
    private Sale sale;

    public Integer getOrderSaleRelationshipId() {
        return orderSaleRelationshipId;
    }

    public void setOrderSaleRelationshipId(Integer orderSaleRelationshipId) {
        this.orderSaleRelationshipId = orderSaleRelationshipId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
        OrderAndSaleRelationship that = (OrderAndSaleRelationship) o;
        return Objects.equals(orderSaleRelationshipId, that.orderSaleRelationshipId) && Objects.equals(order, that.order) && Objects.equals(sale, that.sale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderSaleRelationshipId, order, sale);
    }

    @Override
    public String toString() {
        return "OrderSaleRelationship{" +
                "orderSaleRelationshipId=" + orderSaleRelationshipId +
                ", order=" + order +
                ", sale=" + sale +
                '}';
    }
}
