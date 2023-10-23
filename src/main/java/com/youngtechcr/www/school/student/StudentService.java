package com.youngtechcr.www.school.student;

import com.youngtechcr.www.security.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepo;

    public StudentService(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Transactional(readOnly = true)
    public Student findOrNull(User user) {
        return studentRepo.findByUser(user).orElse(null);
    }
}
