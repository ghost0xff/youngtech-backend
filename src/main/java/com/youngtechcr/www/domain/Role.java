package com.youngtechcr.www.domain;


import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_role")
public class Role {

    @Id
    @Column(name = "id_role")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleId;

    @Column(name = "name")
    private String roleName;

    @OneToMany(mappedBy = "role")
    List<UserAndRoleRelationship> userRoleRelationshipList;

    public Role() {
    }

    public Role(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<UserAndRoleRelationship> getUserRoleRelationshipList() {
        return userRoleRelationshipList;
    }

    public void setUserRoleRelationshipList(List<UserAndRoleRelationship> userRoleRelationshipList) {
        this.userRoleRelationshipList = userRoleRelationshipList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(roleId, role.roleId) && Objects.equals(roleName, role.roleName) && Objects.equals(userRoleRelationshipList, role.userRoleRelationshipList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName, userRoleRelationshipList);
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}