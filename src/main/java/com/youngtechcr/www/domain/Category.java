package com.youngtechcr.www.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_category")
public class Category {

    @Id
    @Column(name = "id_category")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idCategory;
    private String name;




    public Category() {}

    public Category(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public Category(Integer idCategory, String name) {
        this.idCategory = idCategory;
        this.name = name;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(idCategory, category.idCategory) && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategory, name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "idCategory=" + idCategory +
                ", name='" + name + '\'' +
                '}';
    }
}
