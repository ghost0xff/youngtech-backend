package com.youngtechcr.www.school;

import com.youngtechcr.www.school.registration.SchoolRegistrar;
import com.youngtechcr.www.school.registration.SchoolRegistration;
import com.youngtechcr.www.security.user.AuthenticationToUserConverter;
import com.youngtechcr.www.security.user.User;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/school")
public class SchoolController {

    private final SchoolRegistrar schoolRegistrar;
    private final AuthenticationToUserConverter authToUserCnvrtr;
    private final SchoolRepository schoolRepo;

    public SchoolController(
            SchoolRegistrar schoolRegistrar,
            AuthenticationToUserConverter authToUserCnvrtr,
            SchoolRepository schoolRepo
    ) {
        this.schoolRegistrar = schoolRegistrar;
        this.authToUserCnvrtr = authToUserCnvrtr;
        this.schoolRepo = schoolRepo;
    }


    @GetMapping("/{id}/metadata")
    public ResponseEntity<SchoolMetadata> getSomeMetadata(@PathVariable("id") int schooolId) {
        var metadata = schoolRegistrar.usefulSchoolMetadata(schooolId);
        return ResponseEntity.ok(metadata);
    }

    @PostMapping("/members")
    public ResponseEntity<?> registerSchoolMember(
        Authentication authn,
        @RequestParam(required = false, defaultValue = "-1") int schoolId,
        @RequestParam(required = false, defaultValue = "-1") int groupId,
        @RequestParam(required = false, defaultValue = "-1") int subjectId,
        @RequestParam(required = false, defaultValue = "") String comment,
        @RequestParam SchoolMemberType type
    ) {
        User user = authToUserCnvrtr.convert(authn);
        SchoolRegistration registration = new SchoolRegistration(
                schoolId, groupId, subjectId, comment
        );
        schoolRegistrar.register(user, registration, type);
        return ResponseEntity.noContent().build();
    }


}
