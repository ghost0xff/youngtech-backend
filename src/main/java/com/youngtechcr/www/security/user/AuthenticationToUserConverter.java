package com.youngtechcr.www.security.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationToUserConverter
    implements Converter<Authentication, User> {

    private final UserService userService;
    public AuthenticationToUserConverter(
            UserService userService
    ) {
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public User convert(Authentication source) {
        return userService
                .findById(Integer.parseInt(source.getName()));
    }
}
