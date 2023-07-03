package com.youngtechcr.www.security;

import com.youngtechcr.www.user.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<?> signIn(
            @RequestParam List<UserType> types,
            @RequestBody UserCreationMetadata userMetadata
    ) {
        this.userService.createBasicUserAndPerson(userMetadata.user(), userMetadata.person());

    }
}
