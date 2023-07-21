package com.youngtechcr.www.person;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.youngtechcr.www.order.Order;
import com.youngtechcr.www.sale.Sale;
import com.youngtechcr.www.user.User;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_person")
public class Person  {

    @Id
    @Column(name = "id_person")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personId;
    @OneToOne
    @JoinColumn(name = "fk_id_user", referencedColumnName = "id_user")
    private User user;
    private String firstname;
    private String lastname;
    private int age;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;
    @OneToMany(mappedBy = "person")
    private List<Order> orderList;
    @OneToMany(mappedBy = "person")
    private List<Sale> saleList;

    public Person() { }

    public Person(User user) {
        this.user = user;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @JsonManagedReference(value = "person-order")
    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @JsonManagedReference(value = "person-sale")
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
        Person person = (Person) o;
        return age == person.age && Objects.equals(personId, person.personId) && Objects.equals(user, person.user) && Objects.equals(firstname, person.firstname) && Objects.equals(lastname, person.lastname) && Objects.equals(birthdate, person.birthdate) && Objects.equals(orderList, person.orderList) && Objects.equals(saleList, person.saleList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, user, firstname, lastname, age, birthdate, orderList, saleList);
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", user=" + user +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", birthdate=" + birthdate +
//                ", orderList=" + orderList +
//                ", saleList=" + saleList +
                '}';
    }
}
