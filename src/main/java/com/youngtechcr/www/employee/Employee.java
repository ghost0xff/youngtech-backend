package com.youngtechcr.www.employee;

import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.security.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_employee")
public class Employee implements Timestamped {
    @Id
    @Column(name = "id_employee")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "fk_id_user",
            referencedColumnName = "id_user")
    private User user;
    private float salary;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;

    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Float.compare(salary, employee.salary) == 0 && Objects.equals(id, employee.id) && Objects.equals(user, employee.user) && Objects.equals(createdAt, employee.createdAt) && Objects.equals(updatedAt, employee.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, salary, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", salary=" + salary +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
