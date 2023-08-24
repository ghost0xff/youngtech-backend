package com.youngtechcr.www.security.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;
    private final UserToUserDtoConverter userToUserDtoConverter;

    public UserController(
            UserService userService,
            UserDtoToUserConverter userDtoToUserConverter,
            UserToUserDtoConverter userToUserDtoConverter) {
        this.userService = userService;
        this.userToUserDtoConverter = userToUserDtoConverter;
    }



}


