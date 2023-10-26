package com.youngtechcr.www.school;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.school.group.SchoolGroup;
import com.youngtechcr.www.school.registration.SchoolUnspecifiedRegistration;
import com.youngtechcr.www.school.student.Student;
import com.youngtechcr.www.school.subject.SchoolSubject;
import com.youngtechcr.www.school.teacher.Teacher;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_school")
public class School implements Timestamped {


    @Id
    @Column(name = "id_school")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "school")
    private List<SchoolSubject> subjects;
    @OneToMany(mappedBy = "school")
    private List<SchoolGroup> groups;
    @OneToMany(mappedBy = "school")
    private List<Teacher> teachers;
    @OneToMany(mappedBy = "school")
    private List<Student> students;
    @OneToMany(mappedBy = "school")
    private List<SchoolUnspecifiedRegistration> unspecifiedRegistrations;

    public School() {}

    @JsonManagedReference("school-teachers")
    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @JsonManagedReference("school-students")
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @JsonManagedReference(value = "school-groups")
    public List<SchoolGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<SchoolGroup> groups) {
        this.groups = groups;
    }

    @JsonManagedReference(value = "school-subject")
    public List<SchoolSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SchoolSubject> subjects) {
        this.subjects = subjects;
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
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt ;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonManagedReference("school-unspecifiedRegistrations")
    public List<SchoolUnspecifiedRegistration> getUnspecifiedRegistrations() {
        return unspecifiedRegistrations;
    }

    public void setUnspecifiedRegistrations(List<SchoolUnspecifiedRegistration> unspecifiedRegistrations) {
        this.unspecifiedRegistrations = unspecifiedRegistrations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        School school = (School) o;

        if (!Objects.equals(id, school.id)) return false;
        if (!Objects.equals(name, school.name)) return false;
        if (!Objects.equals(createdAt, school.createdAt)) return false;
        if (!Objects.equals(updatedAt, school.updatedAt)) return false;
        if (!Objects.equals(subjects, school.subjects)) return false;
        if (!Objects.equals(groups, school.groups)) return false;
        if (!Objects.equals(teachers, school.teachers)) return false;
        if (!Objects.equals(students, school.students)) return false;
        return Objects.equals(unspecifiedRegistrations, school.unspecifiedRegistrations);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (subjects != null ? subjects.hashCode() : 0);
        result = 31 * result + (groups != null ? groups.hashCode() : 0);
        result = 31 * result + (teachers != null ? teachers.hashCode() : 0);
        result = 31 * result + (students != null ? students.hashCode() : 0);
        result = 31 * result + (unspecifiedRegistrations != null ? unspecifiedRegistrations.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
