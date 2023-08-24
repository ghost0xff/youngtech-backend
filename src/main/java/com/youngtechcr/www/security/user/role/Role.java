package com.youngtechcr.www.security.user.role;


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
    private Integer roleId;
    @Column(name = "name")
    private String name;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Role() {
    }
    public Role(Integer roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    public Role(Integer roleId) {
        this.roleId = roleId;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
        return Objects.equals(roleId, role.roleId) && Objects.equals(name, role.name) && Objects.equals(createdAt, role.createdAt) && Objects.equals(updatedAt, role.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, name, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}