package com.youngtechcr.www.sale;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.youngtechcr.www.customer.Customer;
import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.order.Order;
import com.youngtechcr.www.product.Product;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_sale")
public class Sale implements Timestamped {

    @Id
    @Column(name = "id_sale")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    private float reveneu;
    @Column(name = "tax_debt")
    private float taxDebt;
    @OneToOne
    @JoinColumn(name = "fk_id_order", referencedColumnName = "id_order")
    private Order order;

    public Sale() {
    }

    public Sale(Integer idSale) {
        this.id = idSale;
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

    public float getReveneu() {
        return reveneu;
    }

    public void setReveneu(float reveneu) {
        this.reveneu = reveneu;
    }

    public float getTaxDebt() {
        return taxDebt;
    }

    public void setTaxDebt(float taxDebt) {
        this.taxDebt = taxDebt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sale sale = (Sale) o;
        if (Float.compare(reveneu, sale.reveneu) != 0) return false;
        if (Float.compare(taxDebt, sale.taxDebt) != 0) return false;
        if (createdAt != null ? !createdAt.equals(sale.createdAt) : sale.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(sale.updatedAt) : sale.updatedAt != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (reveneu != 0.0f ? Float.floatToIntBits(reveneu) : 0);
        result = 31 * result + (taxDebt != 0.0f ? Float.floatToIntBits(taxDebt) : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "idSale=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
