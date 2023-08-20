package com.youngtechcr.www.person;

import com.youngtechcr.www.domain.Validator;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.regex.RegexService;
import com.youngtechcr.www.regex.Regexes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PersonValidator implements Validator<Person> {

    private static final Logger log = LoggerFactory.getLogger(PersonValidator.class);
    private final RegexService regexService;

    public PersonValidator(RegexService regexService) {
        this.regexService = regexService;
    }

    @Override
    public boolean isValid(Person personToBeValidated, boolean isUpdate) throws InvalidElementException {
        if (personToBeValidated == null) {
            throw new InvalidElementException(HttpErrorMessages.INVALID_PERSON_REASON_NULL);
        }
        String firstname = personToBeValidated.getFirstname();
        String lastname = personToBeValidated.getLastname();
        int age = personToBeValidated.getAge();

        if(isUpdate) {

        }

        // Validations are made this way because values in DB MUST be either NULL or valid
        return
                (firstname == null || isNameValid(firstname))
                && (lastname == null || isLastNameValid(lastname))
                && (isAgeValid(age));

    }


    private boolean isNameValid(String name) {
        if(regexService.matches(Regexes.PERSON_NAME_AND_LASTNAME_PATTERN, name)) {
            return true;
        }
        throw new InvalidElementException(HttpErrorMessages.INVALID_PERSON_REASON_NAME);
    }

    private boolean isLastNameValid(String lastname) {
        if(regexService.matches(Regexes.PERSON_NAME_AND_LASTNAME_PATTERN, lastname)){
            return true;
        }
        throw new InvalidElementException(HttpErrorMessages.INVALID_PERSON_REASON_LASTNAME);
    }

    private boolean isAgeValid(int age) {
        if( age > 0 && age < 100 ) {
            return true;
        }
        throw new InvalidElementException(HttpErrorMessages.INVALID_PERSON_REASON_AGE);
    }


}
