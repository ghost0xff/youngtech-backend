package com.youngtechcr.www.security.user;


import com.youngtechcr.www.security.user.role.Role;

import java.util.List;

public record UserDto(
        String email,
        List<Role> roleList
) {
}
