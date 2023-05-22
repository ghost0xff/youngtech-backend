package com.youngtechcr.www.domain;


import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_user")
public class User  {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idUser;

    @OneToMany(mappedBy = "user")
    private List<UserAndRoleRelationship> userRoleRelationshipList;

    @OneToMany(mappedBy = "user")
    private List<Sale> saleList;

    private String username;
    private String password;
    private String email;

    public User(){}

    public User(Integer idUser) {
        this.idUser = idUser;
    }

    public User(Integer idUser, String username, String password, String email) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserAndRoleRelationship> getUserRoleRelationshipList() {
        return userRoleRelationshipList;
    }

    public void setUserRoleRelationshipList(List<UserAndRoleRelationship> userRoleRelationshipList) {
        this.userRoleRelationshipList = userRoleRelationshipList;
    }

    public List<Sale> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<Sale> saleList) {
        this.saleList = saleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(idUser, user.idUser) && Objects.equals(userRoleRelationshipList, user.userRoleRelationshipList) && Objects.equals(saleList, user.saleList) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, userRoleRelationshipList, saleList, username, password, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
