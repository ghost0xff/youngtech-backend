package com.youngtechcr.www.security.oidc;

import com.youngtechcr.www.school.student.StudentService;
import com.youngtechcr.www.school.teacher.TeacherService;
import com.youngtechcr.www.security.CustomClaims;
import com.youngtechcr.www.security.user.User;
import com.youngtechcr.www.security.user.UserService;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OidcUserInfoService {

    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public OidcUserInfoService(
            UserService userService,
            StudentService studentService,
            TeacherService teacherService
    ) {
        this.userService = userService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    public OidcUserInfo loadUserById(int id) {
        User user = userService.findById(id);
        var student = studentService.findOrNull(user);
        var teacher = teacherService.findOrNull(user);
        Set<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet());
        OidcUserInfo info = OidcUserInfo.builder()
            .subject(String.valueOf(user.getId()))
            .email(user.getEmail())
            .emailVerified(user.isEmailVerified())
            .givenName(user.getPerson().getFirstnames()) // lastname
            .familyName(user.getPerson().getLastnames()) // lastnames
            .updatedAt(user.getLastUpdateAt().toString())
//            .claim("sid", "none")
                .claim(CustomClaims.STUDENT, student)
                .claim(CustomClaims.TEACHER, teacher)
            .claim(CustomClaims.ROLES, roles)
            .build();
        return info;

        /*
        * TODO: add all of the above :v
        * */
//        builder.address(null);
//        builder.birthdate(null);
//        builder.address(null);
//        builder.profile(null);
//        builder.website(null)
//        builder.locale(null);
//        builder.gender(null);
//        builder.middlename(middleName);
//        builder.name(middleName);
//        builder.nickname(middleName);
//        builder.picture(middleName);
//        builder.phoneNumber(middleName);
//        builder.phoneNumberVerified(middleName);
//        builder.preferredUsername(middleName);
//        return null;
    }

}
