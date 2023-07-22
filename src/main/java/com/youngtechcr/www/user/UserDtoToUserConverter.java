package com.youngtechcr.www.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class UserDtoToUserConverter implements Converter<UserDto, User> {
    @Override
    public User convert(UserDto source) {
        return new User(
                source.email(),
                source.roleList()
        );
    }
}
