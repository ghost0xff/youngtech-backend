package com.youngtechcr.www.user;

import com.youngtechcr.www.domain.Validator;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.regex.RegexService;
import com.youngtechcr.www.regex.Regexes;
import com.youngtechcr.www.user.role.Role;
import com.youngtechcr.www.user.role.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserValidator implements Validator<User> {

    private final RegexService regexService;
    private final RoleService roleService;

    public UserValidator(RegexService regexService, RoleService roleService) {
        this.regexService = regexService;
        this.roleService = roleService;
    }

    @Override
    public boolean isValid(User userToBeValidated) {
        return
                this.validateUsername(userToBeValidated.getUsername()) &&
                this.validatePassword(userToBeValidated.getPassword()) &&
                this.validateEmail(userToBeValidated.getEmail()) &&
                this.validateRoles(userToBeValidated.getRoles());
    }

    private boolean validateUsername(String username) {
        if(!StringUtils.hasText(username)) {
            throw new InvalidElementException(HttpErrorMessages.INVALID_USER_REASON_USERNAME);
        }
        return this.regexService.matches(Regexes.REGEX_USERNAME_PATTERN, username);
    }

    private boolean validatePassword(String password) {
        if(!StringUtils.hasText(password)) {
            throw new InvalidElementException(HttpErrorMessages.INVALID_USER_REASON_PASSWORD);
        }
        return this.regexService.matches(Regexes.REGEX_PASSWORD_PATTERN, password);
    }

    private boolean validateEmail(String email) {
        if(!StringUtils.hasText(email)) {
            throw new InvalidElementException(HttpErrorMessages.INVALID_USER_REASON_EMAIL);
        }
        return this.regexService.matches(Regexes.REGEX_EMAIL_PATTERN, email);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private boolean validateRoles(List<Role> roles) {
        if(roles.isEmpty()) {
            throw new InvalidElementException(HttpErrorMessages.INVALID_USER_REASON_EMPTY_ROLES);
        }
        return this.roleService.doThisRolesExist(roles);
    }
}
