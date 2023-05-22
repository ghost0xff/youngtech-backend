package com.youngtechcr.www.domain;


import com.youngtechcr.www.domain.storage.ProductAndProductImageRelationship;
import com.youngtechcr.www.domain.storage.ProductImageFileData;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product")
    private Integer idProduct;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_brand", referencedColumnName = "id_brand")
    private Brand brand;


    @OneToMany(mappedBy = "product")
    private List<ProductAndProductImageRelationship> productImageList;

    @OneToMany(mappedBy = "product")
    private List<Sale> saleList;

    private String name;
    private Integer stock;
    private String description;
    private float price;

    private float discount;

    public Product() {
    }

    public Product(Integer idProduct) {
        this.idProduct = idProduct;
    }


    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public List<Sale> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<Sale> saleList) {
        this.saleList = saleList;
    }


}