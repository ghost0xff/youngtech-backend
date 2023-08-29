package com.youngtechcr.www.person;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.youngtechcr.www.order.Order;
import com.youngtechcr.www.sale.Sale;
import com.youngtechcr.www.security.user.User;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_person")
@JsonDeserialize(builder = Person.Builder.class)
public class Person  {

    @Id
    @Column(name = "id_person")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "fk_id_user", referencedColumnName = "id_user")
    private User user;
    private String firstnames;
    private String lastnames;
    private int age;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private List<Order> orders;
    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private List<Sale> sales;

    public Person() { }

    private Person(
            Integer id,
            User user,
            String firstnames,
            String lastnames,
            int age,
            LocalDate birthdate,
            List<Order> orders,
            List<Sale> sales
    ) {
        this.id = id;
        this.user = user;
        this.firstnames = firstnames;
        this.lastnames = lastnames;
        this.age = age;
        this.birthdate = birthdate;
        this.orders = orders;
        this.sales = sales;
    }

    public Person(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    @JsonBackReference("user-person")
    public User getUser() {
        return user;
    }

    public String getFirstnames() {
        return firstnames;
    }


    public String getLastnames() {
        return lastnames;
    }

    public int getAge() {
        return age;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }


    @JsonManagedReference(value = "person-order")
    public List<Order> getOrders() {
        return orders;
    }

    @JsonManagedReference(value = "person-sale")
    public List<Sale> getSales() {
        return sales;
    }



    public static Builder builder() {
        return new Builder();
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(id, person.id) && Objects.equals(user, person.user) && Objects.equals(firstnames, person.firstnames) && Objects.equals(lastnames, person.lastnames) && Objects.equals(birthdate, person.birthdate) && Objects.equals(orders, person.orders) && Objects.equals(sales, person.sales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, firstnames, lastnames, age, birthdate, orders, sales);
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + id +
                ", user=" + user +
                ", firstname='" + firstnames + '\'' +
                ", lastname='" + lastnames + '\'' +
                ", age=" + age +
                ", birthdate=" + birthdate +
//                ", orderList=" + orderList +
//                ", saleList=" + saleList +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {

        private Integer id;
        private User user;
        private String firstnames;
        private String lastnames;
        private int age;
        private LocalDate birthdate;
        private List<Order> orderList;
        private List<Sale> saleList;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder firstnames(String firstname) {
            this.firstnames = firstname;
            return this;
        }

        public Builder lastnames(String lastname) {
            this.lastnames = lastname;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder birthdate(LocalDate birthdate) {
            this.birthdate = birthdate;
            return this;
        }

        public Builder orderList(List<Order> orderList) {
            this.orderList = orderList;
            return this;
        }

        public Builder saleList(List<Sale> saleList) {
            this.saleList = saleList;
            return this;
        }

        public Person build() {
            return new Person(
                this.id,
                this.user,
                this.firstnames,
                this.lastnames,
                this.age,
                this.birthdate,
                this.orderList,
                this.saleList
            );
        }
    }
}
