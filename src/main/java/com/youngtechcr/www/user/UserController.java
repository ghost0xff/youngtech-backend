package com.youngtechcr.www.user;

import com.youngtechcr.www.http.ResponseEntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<UserDto> createUser(
            @RequestBody User userToBeCreated,
            @RequestParam("t") List<UserType> userType
    ) {
//        User createdUser = this.userService.createBasicUserAndPerson(userToBeCreated);
//        UserDto userDto = this.userToUserDtoConverter.convert(createdUser);
        return ResponseEntityUtils.created(null);
    }
}
