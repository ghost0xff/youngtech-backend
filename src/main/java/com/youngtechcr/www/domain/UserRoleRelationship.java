package com.youngtechcr.www.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_user_role")
public class UserRoleRelationship {

    @Id
    @Column(name = "id_user_role")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userRoleRelationshipId;

    @JoinColumn(name = "fk_id_user", referencedColumnName = "id_user")
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @JoinColumn(name = "fk_id_role", referencedColumnName = "id_role")
    @OneToOne(cascade = CascadeType.ALL)
    private Role role;

    public UserRoleRelationship() {}

    public UserRoleRelationship(Integer userRoleRelationshipId, User user, Role role) {
        this.userRoleRelationshipId = userRoleRelationshipId;
        this.user = user;
        this.role = role;
    }

    public Integer getUserRoleRelationshipId() {
        return userRoleRelationshipId;
    }

    public void setUserRoleRelationshipId(Integer userRoleRelationshipId) {
        this.userRoleRelationshipId = userRoleRelationshipId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleRelationship that = (UserRoleRelationship) o;
        return Objects.equals(userRoleRelationshipId, that.userRoleRelationshipId) && Objects.equals(user, that.user) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRoleRelationshipId, user, role);
    }

    @Override
    public String toString() {
        return "UserRoleRelationship{" +
                "userRoleRelationshipId=" + userRoleRelationshipId +
                ", user=" + user +
                ", role=" + role +
                '}';
    }
}

