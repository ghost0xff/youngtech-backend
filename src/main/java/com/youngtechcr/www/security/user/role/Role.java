package com.youngtechcr.www.security.user.role;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youngtechcr.www.domain.Timestamped;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_role")
public class Role implements Timestamped {

    @Id
    @Column(name = "id_role")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "created_at")
    @JsonIgnore
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    public Role() {
    }
    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(Integer id) {
        this.id = id;
    }


    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime timestamp) {
        this.createdAt = timestamp;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime timestamp) {
        this.updatedAt = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

        Role role = (Role) o;

        if (!Objects.equals(id, role.id)) return false;
        if (!Objects.equals(name, role.name)) return false;
        if (!Objects.equals(createdAt, role.createdAt)) return false;
        return Objects.equals(updatedAt, role.updatedAt);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}