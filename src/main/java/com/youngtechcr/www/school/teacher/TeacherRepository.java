package com.youngtechcr.www.school.teacher;

import com.youngtechcr.www.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends
        JpaRepository<Teacher, Integer> {

    Optional<Teacher> findByUser(User user);
}
