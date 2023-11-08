package com.youngtechcr.www.security.user.emailpasswd;

import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.security.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_user_email_password")
public class EmailPasswdUser implements Timestamped {

    @Id
    @Column(name = "id_user_email_password")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String password;

    @OneToOne
    @JoinColumn(name = "fk_id_user", referencedColumnName = "id_user")
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public EmailPasswdUser() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailPasswdUser that = (EmailPasswdUser) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(password, that.password)) return false;
        if (!Objects.equals(user, that.user)) return false;
        if (!Objects.equals(createdAt, that.createdAt)) return false;
        return Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String
    toString() {
        return "EmailPasswdUser{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
