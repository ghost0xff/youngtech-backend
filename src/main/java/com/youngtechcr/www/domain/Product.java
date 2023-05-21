package com.youngtechcr.www.domain;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product")
    private Integer idProduct;

    @JoinColumn(name = "id_brand", referencedColumnName = "id_brand")
    @OneToOne(cascade = CascadeType.ALL)
    private Brand brand;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_product_image", referencedColumnName = "id_product_image")
    private ProductImage productImage;
    private String name;
    private Integer stock;
    private String description;
    private float price;

    private float discount;

    public Product() {
    }

    public Product(int idProduct) {
        this.idProduct = idProduct;
    }

    public Product(Integer idProduct, Brand brand, String name, Integer stock, String description, float price, ProductImage productImage, float discount) {
        this.idProduct = idProduct;
        this.brand = brand;
        this.name = name;
        this.stock = stock;
        this.description = description;
        this.price = price;
        this.productImage = productImage;
        this.discount = discount;
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

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return idProduct == product.idProduct && Float.compare(product.price, price) == 0 && Float.compare(product.discount, discount) == 0 && Objects.equals(brand, product.brand) && Objects.equals(name, product.name) && Objects.equals(stock, product.stock) && Objects.equals(description, product.description) && Objects.equals(productImage, product.productImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, brand, name, stock, description, price, productImage, discount);
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", brand=" + brand +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", productImage=" + productImage +
                ", discount=" + discount +
                '}';
    }
}