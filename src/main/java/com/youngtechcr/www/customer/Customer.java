package com.youngtechcr.www.customer;

import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.person.Person;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_customer")
public class Customer implements Timestamped {

    @Id
    @Column(name = "id_customer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @OneToOne
    @JoinColumn(name = "fk_id_person", referencedColumnName = "id_person")
    private Person person;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId) && Objects.equals(person, customer.person) && Objects.equals(createdAt, customer.createdAt) && Objects.equals(updatedAt, customer.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, person, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", person=" + person +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
