package com.youngtechcr.www.school.group;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.school.School;
import com.youngtechcr.www.school.subject.SchoolSubject;
import com.youngtechcr.www.school.student.Student;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "tbl_school_group")
public class SchoolGroup implements Timestamped {
    @Id
    @Column(name = "id_school_group")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "fk_id_school", referencedColumnName = "id_school")
    private School school;
    @ManyToOne
    @JoinColumn( name = "fk_id_school_technical_subject",
    referencedColumnName = "id_school_subject" )
    private SchoolSubject technicalSubject;
    @OneToMany(mappedBy = "group")
    private List<Student> students;



    @JsonManagedReference("group-students")
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonBackReference(value = "school-groups")
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @JsonBackReference("technicalSubject-groups")
    public SchoolSubject getTechnicalSubject() {
        return technicalSubject;
    }

    public void setTechnicalSubject(SchoolSubject technicalSubject) {
        this.technicalSubject = technicalSubject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchoolGroup that = (SchoolGroup) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(createdAt, that.createdAt)) return false;
        if (!Objects.equals(updatedAt, that.updatedAt)) return false;
        if (!Objects.equals(school, that.school)) return false;
        return Objects.equals(technicalSubject, that.technicalSubject);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (school != null ? school.hashCode() : 0);
        result = 31 * result + (technicalSubject != null ? technicalSubject.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SchoolGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
