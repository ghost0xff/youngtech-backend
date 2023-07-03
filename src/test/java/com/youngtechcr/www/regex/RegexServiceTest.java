package com.youngtechcr.www.regex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegexServiceTest {

    private static final Logger log = LoggerFactory.getLogger(RegexService.class);
    private RegexService regexService;
    private String usernameRegex;
    private String passwordRegex;

    @BeforeEach
    void setUp() {
        this.regexService = new RegexService();
        this.usernameRegex = "^(?=[\\S]{3,20}$)[^0-9][a-zA-Z0-9_@$!]+$";
        this.passwordRegex = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[\\d])(?=.*?[^\\sa-zA-Z0-9]).{8,}";
    }

    @Test
    @DisplayName("Scenario -> Invalid text matched against valid regex should return false")
    void given_Invalid_Text_Should_Return_False() {
        String invalidUsername = "s#a";
        boolean result = this.regexService.matches(this.usernameRegex, invalidUsername);
        assertFalse(result);
    }

    @Test
    @DisplayName("Scenario -> text matched against valid regex should return true")
    void given_Valid_Text_Should_Return_True() {
        String validUsername = "samuel12@!__";
        boolean result = this.regexService.matches(usernameRegex, validUsername);
        assertTrue(result);
    }

    @Test
    @DisplayName("Scenario -> InValid blank/no_text/empty string matched against valid regex should return false")
    void given_Invalid_EmptyString_Should_Return_False() {
        String validUsername = "";
        boolean result = this.regexService.matches(usernameRegex, validUsername);
        assertFalse(result);
    }


    @Test
    @DisplayName("Null regex should throw RegexMatchingException")
    void given_Null_Regex_Should_Throw_RegexMatchingException() {
        String validUsername = "samuel12@!__";
        assertThrows( RegexMatchingException.class , () -> {
            boolean result = this.regexService.matches(null, validUsername);
        });
    }

    @Test
    @DisplayName("Null text should throw RegexMatchingException")
    void given_Null_Text_Should_Throw_RegexMatchingException() {
        assertThrows( RegexMatchingException.class , () -> {
            boolean result = this.regexService.matches(this.usernameRegex, null);
        });
    }

    @Test
    @DisplayName("Scenario -> InValid password matched against valid regex should return false")
    void given_Invalid_Password_Should_Return_False() {
        String invalidPasssword = "PapasF";
        boolean result = this.regexService.matches(passwordRegex, invalidPasssword);
        assertFalse(result);
    }

    @Test
    @DisplayName("Scenario -> Valid password matched against valid regex should return true")
    void given_Valid_Password_Should_Return_True() {
        String invalidPasssword = "Password1231_";
        boolean result = this.regexService.matches(passwordRegex, invalidPasssword);
        assertTrue(result);
    }

}