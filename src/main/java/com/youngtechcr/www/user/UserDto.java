package com.youngtechcr.www.user;


import com.youngtechcr.www.user.role.Role;

import java.util.List;

public record UserDto(
        String email,
        List<Role> roleList
) {
}
