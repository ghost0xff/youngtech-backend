package com.youngtechcr.www.user;

import com.youngtechcr.www.domain.Validator;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.regex.RegexService;
import com.youngtechcr.www.regex.Regexes;
import org.springframework.stereotype.Service;

@Service
public class BasicUserValidator implements Validator<BasicUser> {

    private final RegexService regexService;

    public BasicUserValidator(RegexService regexService) {
        this.regexService = regexService;
    }

    @Override
    public boolean isValid(BasicUser basicUser, boolean isUpdate ){
        return
                this.validatePassword(basicUser.password())
                && this.validateEmail(basicUser.email());
    }

    private boolean validatePassword(String password) {
        if(regexService.matches(Regexes.PASSWORD_PATTERN, password) ) {
           return true;
        }
        throw new InvalidElementException(HttpErrorMessages.INVALID_USER_REASON_PASSWORD);
    }

    private boolean validateEmail(String email) {
        if(regexService.matches(Regexes.EMAIL_PATTERN, email)) {
            return true;
        }
        throw new InvalidElementException(HttpErrorMessages.INVALID_USER_REASON_EMAIL);
    }

}
