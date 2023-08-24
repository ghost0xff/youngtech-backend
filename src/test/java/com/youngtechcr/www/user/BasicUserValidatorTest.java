package com.youngtechcr.www.user;

import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.regex.RegexService;
import com.youngtechcr.www.security.user.BasicUser;
import com.youngtechcr.www.security.user.BasicUserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
class BasicUserValidatorTest {

    private BasicUserValidator basicUserValidator;
    private RegexService regexService;

//
//    @BeforeEach
//    void setUp() {
//        this.regexService = new RegexService();
//        this.basicUserValidator = new BasicUserValidator(regexService);
//    }
//
//    @Test
//    @DisplayName("Valid user should return true")
//    void given_Valid_BasicUser_Should_Return_True() {
//        BasicUser userToBeValidated = new BasicUser(
//                "samuelastuaflores@gmail.com"
//                , "Is this @ valiod2 Passwqworord??"
//        );
//        assertTrue(this.basicUserValidator.isValid(userToBeValidated));
//    }
//
//
//    @Test
//    @DisplayName("Validation of user with password containing whitespace in the middle should return true")
//    void given_BasicUser_With_Password_Containing_Whitespace_In_The_Middle_Should_Return_True() {
//        BasicUser userToBeValidated = new BasicUser("samuelastuaflores@gmail.com",
//                 "   #pas55 asd woIrd");
//        assertTrue(this.basicUserValidator.isValid(userToBeValidated));
//    }
//
//
//    @Test
//    @DisplayName("BasicUser with invalid passwd should throw InvalidElementException")
//    void given_BasicUser_With_Invalid_Password_Should_Return_False() {
//        BasicUser userToBeValidated = new BasicUser(
//                "samuelastuaflores@gmail.com"
//                , ": dSda"
//        );
//        assertThrows(InvalidElementException.class, () -> {
//            basicUserValidator.isValid(userToBeValidated);
//        });
//    }
//
//    @Test
//    @DisplayName("BasicUser with invalid email should throw InvalidElementException")
//    void given_BasicUser_With_Invalid_Email_Should_Return_False() {
//        BasicUser userToBeValidated = new BasicUser(
//                "samuelastuadflores@gmail.com"
//                ,
//                "PO0000000^&sds"
//        );
//        assertThrows(InvalidElementException.class, () -> {
//           assertFalse(this.basicUserValidator.isValid(userToBeValidated));
//        });
//    }

}