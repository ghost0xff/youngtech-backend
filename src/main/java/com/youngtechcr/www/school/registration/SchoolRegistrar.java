package com.youngtechcr.www.school.registration;

import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.domain.TimestampedUtils;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.school.*;
import com.youngtechcr.www.school.group.SchoolGroupRepository;
import com.youngtechcr.www.school.student.Student;
import com.youngtechcr.www.school.student.StudentRepository;
import com.youngtechcr.www.school.subject.SchoolSubject;
import com.youngtechcr.www.school.subject.SchoolSubjectRepository;
import com.youngtechcr.www.school.teacher.Teacher;
import com.youngtechcr.www.school.teacher.TeacherRepository;
import com.youngtechcr.www.security.annotations.roles.CustomerRole;
import com.youngtechcr.www.security.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolRegistrar {

    private final StudentRepository studentRepo;
    private final TeacherRepository teacherRepo;
    private final SchoolRepository schoolRepo;
    private final SchoolGroupRepository schoolGroupRepo;
    private final SchoolSubjectRepository schoolSubjRepo;
    private final SchoolUnspecifiedRegistrationRepo unspecRegRepo;

    public SchoolRegistrar(
            SchoolUnspecifiedRegistrationRepo unspecRegRepo,
            StudentRepository studentRepo,
            TeacherRepository teacherRepo,
            SchoolRepository schoolRepo,
            SchoolGroupRepository schoolGroupRepo,
            SchoolSubjectRepository schoolSubjRepo
    ) {
        this.unspecRegRepo = unspecRegRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.schoolRepo = schoolRepo;
        this.schoolGroupRepo = schoolGroupRepo;
        this.schoolSubjRepo = schoolSubjRepo;
    }

    @Transactional
    @CustomerRole
    public void register(User user, SchoolRegistration registration
    ) {
        switch (registration.type()) {
            case OTHER -> {
                var school = schoolRepo
                        .findById(registration.schoolId())
                        .orElseThrow(() -> {
                            return new NoDataFoundException(
                                    HttpErrorMessages
                                            .NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND
                            );
                        });
                String comment = registration.comment();
                if(!StringUtils.hasText(comment)) {
                    throw new InvalidElementException(
                        HttpErrorMessages.INVALID_COMMENT_ON_UNSPECIFIED_REGISTRATION
                    );
                }
                var unspecReg = new SchoolUnspecifiedRegistration();
                TimestampedUtils.setTimestampsToNow(unspecReg);
                unspecReg.setSchool(school);
                unspecReg.setUser(user);
                unspecReg.setComment(comment);
                unspecRegRepo.save(unspecReg);
                return;
            }
            case STUDENT -> {
                var schoolGroup = schoolGroupRepo
                        .findById(registration.groupId())
                        .orElseThrow(() -> {
                            return new NoDataFoundException(
                                HttpErrorMessages
                                .NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND
                            );
                        });
                var student = new Student();
                student.setGroup(schoolGroup);
                student.setSchool(schoolGroup.getSchool());
                student.setUser(user);
                TimestampedUtils.setTimestampsToNow(student);
                studentRepo.save(student);
            }
            case TEACHER -> {
                var schoolSubject = schoolSubjRepo
                        .findById(registration.subjectId())
                        .orElseThrow(() -> {
                            return new NoDataFoundException(
                                HttpErrorMessages
                                .NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND
                            );
                        });
                var teacher = new Teacher();
                teacher.setUser(user);
                teacher.setSchool(schoolSubject.getSchool());
                teacher.setSubject(schoolSubject);
                TimestampedUtils.setTimestampsToNow(teacher);
                teacherRepo.save(teacher);
            }
        }
    }


    @Transactional(readOnly = true)
    @CustomerRole
    public SchoolMetadata usefulSchoolMetadata(int schoolId) {
        School school = schoolRepo
                .findById(schoolId)
                .orElseThrow(() ->
                    new NoDataFoundException(
                        HttpErrorMessages
                        .NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND
                    ));
        List<SchoolSubject> technicalSubjects = school.getSubjects()
                .stream()
                .filter(subject -> subject.isTechnical())
                .collect(Collectors.toList());
        List<SchoolSubject> academicSubjects = school.getSubjects()
                .stream()
                .filter(subject -> !subject.isTechnical())
                .collect(Collectors.toList());
        return new SchoolMetadata(technicalSubjects, academicSubjects);
    }



    @Transactional(readOnly = true)
    public UserSchoolMetadata userSchoolMetadata(User user) {

        // check for existing students
        var student = studentRepo .findByUser(user) .orElse(null);
        if(student != null) {
            return new UserSchoolMetadata(
                    true,
                    SchoolMemberType.STUDENT,
                    student.getSchool().getId()
            );
        }
        // or check for existing teachers
        var teacher = teacherRepo.findByUser(user).orElse(null);
        if(teacher != null) {
            return new UserSchoolMetadata(
                    true,
                    SchoolMemberType.TEACHER,
                    teacher.getSchool().getId()
            );
        }
        // or check for existing 'unspecified registrations"
        var unspecReg = unspecRegRepo.findByUser(user).orElse(null);
        if(unspecReg != null) {
            return new UserSchoolMetadata(
                    true,
                    SchoolMemberType.OTHER,
                    unspecReg.getId()
            );
        }

        // Workaround messy code cause only 1 school is planned
        // to be registered (which is CTP Santa Ana)
        School school = schoolRepo
                .findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoDataFoundException(
                        HttpErrorMessages.NO_SCHOOLS_REGISTERED
                ));

        // or just return some nullish metadata (yeah, I just used some
        // javascript slang in this codebase)
        return new UserSchoolMetadata(false, null, school.getId());
                                    // -1 means an "undefined" schoolId ^^^^^^^^
    }

}
















