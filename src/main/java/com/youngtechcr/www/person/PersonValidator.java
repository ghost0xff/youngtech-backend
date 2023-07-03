package com.youngtechcr.www.person;

import com.youngtechcr.www.domain.Validator;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.regex.RegexService;
import com.youngtechcr.www.regex.Regexes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PersonValidator implements Validator<Person> {

    private static final Logger log = LoggerFactory.getLogger(PersonValidator.class);
    private final RegexService regexService;

    public PersonValidator(RegexService regexService) {
        this.regexService = regexService;
    }

    @Override
    public boolean isValid(Person personToBeValidated) {
        if (personToBeValidated == null) return false;
        String firstName = personToBeValidated.getFirstName();
        String secondName = personToBeValidated.getSecondName();
        String firstLastname = personToBeValidated.getFirstLastname();
        String secondLastname = personToBeValidated.getSecondLastname();
        int age = personToBeValidated.getAge();

        // Validations are made this way because values in DB MUST be either NULL or valid
        return
                (firstName == null || isNameValid(firstName)) &&
                (secondName == null || isNameValid(secondName)) &&
                (firstLastname == null || isLastNameValid(firstLastname)) &&
                (secondLastname == null || isLastNameValid(secondLastname)) &&
                (isAgeValid(age));

    }


    private boolean isNameValid(String name) {
        return this
                .regexService
                    .matches(Regexes.REGEX_PERSON_NAME_AND_LASTNAME_PATTERN, name);
    }

    private boolean isLastNameValid(String lastname) {
        return this
                .regexService
                    .matches(Regexes.REGEX_PERSON_NAME_AND_LASTNAME_PATTERN, lastname);
    }

    private boolean isAgeValid(int age) {
        return age > 0 && age < 100;
    }


}
