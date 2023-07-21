package com.youngtechcr.www.user;

import com.youngtechcr.www.person.Person;
import com.youngtechcr.www.profile.Profile;
import com.youngtechcr.www.user.role.Role;
import jakarta.persistence.*;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String password;
    private String email;
    @Column(name = "signed_up_at")
    private LocalDateTime signedUpAt;
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    @Column(name = "last_update_at")
    private LocalDateTime lastUpdateAt;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tbl_user_and_role",
            joinColumns = @JoinColumn(name = "fk_id_user", referencedColumnName = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "fk_id_role", referencedColumnName = "id_role")
    )
    private List<Role> roles;
    @OneToOne(mappedBy = "user")
    private Person person;
    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Profile profile;

    public User() { }

    public User(String email, Collection<Role> roles) {
        this.email = email;
        this.roles = (List<Role>) roles;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public LocalDateTime getSignedUpAt() {
        return signedUpAt;
    }

    public void setSignedUpAt(LocalDateTime signedUpAt) {
        this.signedUpAt = signedUpAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public LocalDateTime getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(LocalDateTime lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(signedUpAt, user.signedUpAt) && Objects.equals(lastLoginAt, user.lastLoginAt) && Objects.equals(lastUpdateAt, user.lastUpdateAt) && Objects.equals(roles, user.roles) && Objects.equals(person, user.person) && Objects.equals(profile, user.profile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, email, signedUpAt, lastLoginAt, lastUpdateAt, roles, person, profile);
    }

    @Override
    public String toString() {
        return "BasicUser{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", signedUpAt=" + signedUpAt +
                ", lastLoginAt=" + lastLoginAt +
                ", lastUpdateAt=" + lastUpdateAt +
                ", roles=" + roles +
                ", person=" + person +
                ", profile=" + profile +
                '}';
    }
}
