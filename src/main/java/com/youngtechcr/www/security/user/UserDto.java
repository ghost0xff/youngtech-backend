package com.youngtechcr.www.security.user;


import com.youngtechcr.www.security.user.role.Role;

import java.util.Set;

public record UserDto(
        String email,
        Set<Role> roles
) {
}
