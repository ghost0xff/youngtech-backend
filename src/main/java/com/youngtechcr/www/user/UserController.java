package com.youngtechcr.www.user;

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

    @PostMapping
    public ResponseEntity<?> createBasicUser( @RequestBody BasicUser user) {
        userService.createUserFromBasicInfo(user);
        return null;
    }


}


