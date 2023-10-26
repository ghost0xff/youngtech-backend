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
        @RequestBody SchoolRegistration registration
    ) {
        User user = authToUserCnvrtr.convert(authn);
        schoolRegistrar.register(user, registration);
        return ResponseEntity.noContent().build();
    }


}
