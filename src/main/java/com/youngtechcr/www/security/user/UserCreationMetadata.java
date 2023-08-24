package com.youngtechcr.www.security.user;

import com.youngtechcr.www.person.Person;

public record UserCreationMetadata(
        User user,
        Person person

) {
}
