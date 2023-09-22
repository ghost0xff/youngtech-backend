package com.youngtechcr.www.sale;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.youngtechcr.www.customer.Customer;
import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.product.Product;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_sale")
public class Sale implements Timestamped {

    @Id
    @Column(name = "id_sale")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSale;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "fk_id_product", referencedColumnName = "id_product")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "fk_id_customer",
            referencedColumnName = "id_customer")
    private Customer customer;

    public Sale() {
    }

    public Sale(Integer idSale) {
        this.idSale = idSale;
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

    public Integer getIdSale() {
        return idSale;
    }

    public void setIdSale(Integer idSale) {
        this.idSale = idSale;
    }

    @JsonManagedReference(value = "product-sale")
    public Product getProduct() {
        return product;
    }

    @JsonBackReference(value = "customer-sale")
    public Customer getCustomer() {
        return customer;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(idSale, sale.idSale) && Objects.equals(createdAt, sale.createdAt) && Objects.equals(updatedAt, sale.updatedAt) && Objects.equals(product, sale.product) && Objects.equals(customer, sale.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSale, createdAt, updatedAt, product, customer);
    }

    @Override
    public String toString() {
        return "Sale{" +
                "idSale=" + idSale +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", product=" + product +
                ", customer=" + customer +
                '}';
    }
}
