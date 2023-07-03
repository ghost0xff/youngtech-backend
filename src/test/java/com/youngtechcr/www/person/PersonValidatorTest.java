package com.youngtechcr.www.person;

import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.regex.RegexService;
import com.youngtechcr.www.user.User;
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
        Person personToBeValidated = new Person();
        personToBeValidated.setFirstName("Samuel");
        personToBeValidated.setSecondName(null);
        personToBeValidated.setFirstLastname("Astua");
        personToBeValidated.setFirstName("Flo");
        personToBeValidated.setAge(78);

        //Assetions
        assertTrue(this.personValidator.isValid(personToBeValidated));

    }

    @Test
    @DisplayName("Invalid person age (too old) should return false")
    void given_Invalid_Person_Age_Too_Old_Should_Return_False() {

        //Preparing data for assertions
        Person personToBeValidated = new Person();
        personToBeValidated.setFirstName("Samuel");
        personToBeValidated.setSecondName(null);
        personToBeValidated.setFirstLastname("Astua");
        personToBeValidated.setFirstName("Flo");
        personToBeValidated.setAge(100); // min:1 & max:99

        //Assetions
        assertFalse(this.personValidator.isValid(personToBeValidated));

    }

    @Test
    @DisplayName("Invalid person age (with young) should return false")
    void given_Invalid_Person_Age_Too_Young_Should_Return_False() {

        //Preparing data for assertions
        Person personToBeValidated = new Person();
        personToBeValidated.setFirstName("Samuel");
        personToBeValidated.setSecondName(null);
        personToBeValidated.setFirstLastname("fdsfds");
        personToBeValidated.setFirstName("Flo");
        personToBeValidated.setAge(-2);// min:1 & max:99

        //Assetions
        assertFalse(this.personValidator.isValid(personToBeValidated));

    }
    /*
    *
    * All subsequent tests are only for firstname and not also for
    * secondName & firstlastname & secondLastname because the same
    * REGEX IS used to validate all 4 values
    *
    * */
    @Test
    @DisplayName("Invalid person with too short name should throe InvalidElementException")
    void given_Invalid_Short_Name_Should_Return_False() {
        //Preparing data for assertions
        Person personToBeValidated = new Person();
        personToBeValidated.setFirstName("a"); // 1 char & min 3 chars
        personToBeValidated.setSecondName(null);
        personToBeValidated.setFirstLastname("Astua");
        personToBeValidated.setSecondLastname("Flo");
        personToBeValidated.setAge(12);
        //Assetions
        assertFalse(personValidator.isValid(personToBeValidated));
    }

    @Test
    @DisplayName("Invalid person with too long name should throe InvalidElementException")
    void given_Invalid_Long_Name_Should_Return_False() {
        //Preparing data for assertions
        Person personToBeValidated = new Person();
        personToBeValidated.setFirstName("aaaaaaaaaaaaaaaaaaaaA"); //21 chars & max 20
        personToBeValidated.setSecondName(null);
        personToBeValidated.setFirstLastname("Astua");
        personToBeValidated.setSecondLastname("Flo");
        personToBeValidated.setAge(12);
        //Assetions
        assertFalse(personValidator.isValid(personToBeValidated));
    }

    @Test
    @DisplayName("Invalid person with too long name should throe InvalidElementException")
    void given_Invalid_Name_With_Numbers_Or_Chars_Should_Return_False() {
        //Preparing data for assertions
        Person personToBeValidated = new Person();
        personToBeValidated.setFirstName("aA1234&*("); //only aplhabetic chars permitted
        personToBeValidated.setSecondName(null);
        personToBeValidated.setFirstLastname("Astua");
        personToBeValidated.setSecondLastname("Flo");
        personToBeValidated.setAge(12);
        //Assetions
        assertFalse(personValidator.isValid(personToBeValidated));
    }

}