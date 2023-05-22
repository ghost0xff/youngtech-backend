package com.youngtechcr.www.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_sale")
public class Sale {

    @Id
    @Column(name = "id_sale")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idSale;

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id_product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

    @OneToMany(mappedBy = "sale")
    List<OrderAndSaleRelationship> orders;

    public Sale() {
    }

    public Sale(Integer idSale) {
        this.idSale = idSale;
    }

    public Sale(Integer idSale, Product product) {
        this.idSale = idSale;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(idSale, sale.idSale) && Objects.equals(product, sale.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSale, product);
    }

    @Override
    public String toString() {
        return "Sale{" +
                "idSale=" + idSale +
                ", product=" + product +
                '}';
    }
}
