package com.youngtechcr.www.user;


import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUserTypeConverter implements Converter<String, UserType> {
    @Override
    public UserType convert(String source) {
        return UserType.valueOf(source.toUpperCase());
    }
}
