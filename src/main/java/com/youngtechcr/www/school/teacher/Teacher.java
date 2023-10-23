package com.youngtechcr.www.school.teacher;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.school.School;
import com.youngtechcr.www.school.subject.SchoolSubject;
import com.youngtechcr.www.security.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_teacher")
public class Teacher implements Timestamped {
    @Id
    @Column(name = "id_teacher")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn( name = "fk_id_school_subject",
            referencedColumnName = "id_school_subject" )
    private SchoolSubject subject;

    @ManyToOne
    @JoinColumn( name = "fk_id_school",
            referencedColumnName = "id_school" )
    private School school;

    @OneToOne
    @JoinColumn(name = "fk_id_user",
            referencedColumnName = "id_user")
    private User user;

    public Teacher() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public SchoolSubject getSubject() {
        return subject;
    }

    public void setSubject(SchoolSubject subject) {
        this.subject = subject;
    }

    @JsonBackReference("school-teachers")
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;

        if (!Objects.equals(id, teacher.id)) return false;
        if (!Objects.equals(createdAt, teacher.createdAt)) return false;
        if (!Objects.equals(updatedAt, teacher.updatedAt)) return false;
        if (!Objects.equals(subject, teacher.subject)) return false;
        return Objects.equals(school, teacher.school);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (school != null ? school.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}