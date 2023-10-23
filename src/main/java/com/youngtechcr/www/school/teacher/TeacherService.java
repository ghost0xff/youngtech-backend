package com.youngtechcr.www.school.teacher;

import com.youngtechcr.www.security.user.User;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepo;

    public TeacherService(TeacherRepository teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    public Teacher findOrNull(User user) {
        return teacherRepo.findByUser(user).orElse(null);
    }

}
