package com.youngtechcr.www.user;

import com.youngtechcr.www.person.Person;
import com.youngtechcr.www.user.profilepicture.ProfilePictureMetaData;
import com.youngtechcr.www.user.role.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @OneToOne(mappedBy = "user")
    private ProfilePictureMetaData profilePicture;
    private String username;
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

    public User() {
    }
    public User(String username, String email, List<Role> roleList) {
        this.username = username;
        this.email = email;
        this.roles = roleList;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ProfilePictureMetaData getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePictureMetaData profilePicture) {
        this.profilePicture = profilePicture;
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
}
