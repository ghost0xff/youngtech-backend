package com.youngtechcr.www.security.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class UserToUserDtoConverter implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User source) {
        return new UserDto(
            source.getEmail(),
            source.getRoles()
        );
    }
}
