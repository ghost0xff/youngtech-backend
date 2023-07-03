package com.youngtechcr.www.user;

import com.youngtechcr.www.regex.RegexService;
import com.youngtechcr.www.user.role.RoleRepository;
import com.youngtechcr.www.user.role.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
class UserValidatorTest {

    private UserValidator userValidator;
    private RegexService regexService;
    private RoleService roleService;
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        this.regexService = new RegexService();
        this.roleRepository = mock(RoleRepository.class);
        this.roleService = new RoleService(roleRepository);
        this.userValidator = new UserValidator(regexService, roleService);
    }

    @Test
    @DisplayName("Valid user should return true")
    void given_Valid_User_Should_Return_True() {
        User userToBeValidated = new User();
        userToBeValidated.setEmail("samuelastuaflores@gmail.com");
        userToBeValidated.setUsername("sam@__");
        userToBeValidated.setPassword("password");
        assertTrue(this.userValidator.isValid(userToBeValidated));
    }


    @Test
    @DisplayName("Validation of user with password containing whitespace in the middle should return true")
    void given_User_With_Password_Containing_Whitespace_In_The_Middle_Should_Return_True() {
        User userToBeValidated = new User();
        userToBeValidated.setEmail("samuelastuaflores@gmail.com");
        userToBeValidated.setUsername("sam@__");
        userToBeValidated.setPassword("passss   word");
        assertTrue(this.userValidator.isValid(userToBeValidated));
    }


    @Test
    @DisplayName("User with invalid passwd should return false")
    void given_User_With_Invalid_Password_Should_Return_False() {
        User userToBeValidated = new User();
        userToBeValidated.setEmail("samuelastuaflores@gmail.com");
        userToBeValidated.setUsername("sam@__");
        userToBeValidated.setPassword(" ddsa da ");
        assertFalse(this.userValidator.isValid(userToBeValidated));
    }

    @Test
    @DisplayName("User with invalid email should return false")
    void given_User_With_Invalid_Email_Should_Return_False() {
        User userToBeValidated = new User();
        userToBeValidated.setEmail("samuelastuafloresgmail.com"); // no @???
        userToBeValidated.setUsername("sam@__");
        userToBeValidated.setPassword("asdda");
        assertFalse(this.userValidator.isValid(userToBeValidated));
    }

}