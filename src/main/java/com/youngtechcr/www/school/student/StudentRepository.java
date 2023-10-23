package com.youngtechcr.www.school.student;

import com.youngtechcr.www.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends
        JpaRepository<Student,Integer> {

    Optional<Student> findByUser(User user);
}
