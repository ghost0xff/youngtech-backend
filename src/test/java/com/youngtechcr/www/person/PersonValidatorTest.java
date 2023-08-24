package com.youngtechcr.www.person;

import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.regex.RegexService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonValidatorTest {

    private PersonValidator personValidator;
    private RegexService regexService;
    @BeforeEach
    void setUp() {
        this.regexService = new RegexService();
        this.personValidator = new PersonValidator(regexService);
    }


    @Test
    @DisplayName("Valid person should return true")
    void given_Valid_Person_Should_Return_True() {

        //Preparing data for assertions
        Person personToBeValidated = Person
                .builder()
                .firstnames("samuel")
                .lastnames(null)
                .age(78)
                .build();

        //Assetions
        assertTrue(this.personValidator.isValid(personToBeValidated, false));

    }

    @Test
    @DisplayName("Invalid person age (too old) should throw InvalidElementException")
    void given_Invalid_Person_Age_Too_Old_Should_Throw_InvalidElementException() {

        //Preparing data for assertions
        Person personToBeValidated = Person
                .builder()
                .firstnames("samuel")
                .lastnames(null)
                .age(100)
                .build();

        //Assetions
        assertThrows(InvalidElementException.class, () -> {
            personValidator.isValid(personToBeValidated, false);
        });

    }

    @Test
    @DisplayName("Invalid person age (with young) should throw InvalidElementException")
    void given_Invalid_Person_Age_Too_Young_Should_Throw_InvalidElementException() {

        //Preparing data for assertions
        Person personToBeValidated = Person
                .builder()
                .firstnames("samuel")
                .lastnames(null)
                .age(-2)
                .build();

        //Assetions
        assertThrows(InvalidElementException.class, () -> {
            personValidator.isValid(personToBeValidated, false);
        });
    }

    @Test
    @DisplayName("Invalid name (too short) should throw InvalidElementException")
    void given_Invalid_Short_Name_Should_Throw_InvalidElementException() {

        //Preparing data for assertions
        Person personToBeValidated = Person
                .builder()
                .firstnames("a")
                .lastnames(null)
                .age(12)
                .build();

        //Assetions
        assertThrows(InvalidElementException.class, () -> {
                personValidator.isValid(personToBeValidated, false);
            });
        }

    @Test
    @DisplayName("Invalid name (too long) should throw InvalidElementException")
    void given_Invalid_Long_Name_Should_Throw_InvalidElementException() {
        //Preparing data for assertions
        Person personToBeValidated = Person
                .builder()
                .firstnames("aaaaaaaaaaaaaaaaaaaaA")
                .lastnames("Flo")
                .age(12)
                .build();
        //Assetions
        assertThrows(InvalidElementException.class, () -> {
            personValidator.isValid(personToBeValidated, false);
        });
    }

    @Test
    @DisplayName("Invalid person with too long name should throw InvalidElementException")
    void given_Invalid_Name_With_Numbers_Or_Chars_Should_Throw_InvalidElementException() {
        //Preparing data for assertions
        Person personToBeValidated = Person
                .builder()
                .firstnames("aA1234&*(")
                .lastnames(null)
                .age(12)
                .build();
        //Assetions
        assertThrows(InvalidElementException.class, () -> {
            personValidator.isValid(personToBeValidated, false);
        });
    }

}