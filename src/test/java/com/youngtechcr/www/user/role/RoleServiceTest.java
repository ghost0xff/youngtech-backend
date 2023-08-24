package com.youngtechcr.www.user.role;

import static org.junit.jupiter.api.Assertions.*;

import com.youngtechcr.www.security.user.role.Role;
import com.youngtechcr.www.security.user.role.RoleOption;
import com.youngtechcr.www.security.user.role.RoleRepository;
import com.youngtechcr.www.security.user.role.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class RoleServiceTest {

    private RoleService roleService;
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        this.roleRepository = mock(RoleRepository.class);
        this.roleService = new RoleService(roleRepository);
    }

    @Test
    @DisplayName("Valid options should map to existing roles")
    void given_Valid_Options_Should_Return_Valid_Roles(){
        // preparing mock
       Role expectedUserRole = new Role(1, "ROLE_USER");
       Role expectedCustomerRole = new Role(2, "ROLE_CUSTOMER");
       Role expectedEmployeeRole = new Role(3, "ROLE_EMPLOYEE");
       Role expectedAdminRole = new Role(4, "ROLE_ADMIN");

        // whens
       when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(expectedUserRole));
       when(roleRepository.findByName("ROLE_CUSTOMER")).thenReturn(Optional.of(expectedCustomerRole));
        when(roleRepository.findByName("ROLE_EMPLOYEE")).thenReturn(Optional.of(expectedEmployeeRole));
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(expectedAdminRole));

       // operations
        List<Role> roles = roleService.
                mapOptionsToRoles(
                        RoleOption.USER,
                        RoleOption.CUSTOMER,
                        RoleOption.EMPLOYEE,
                        RoleOption.ADMIN
                );

        // assertions
        assertTrue(roles.containsAll(
                List.of(
                        expectedCustomerRole,
                        expectedUserRole,
                        expectedUserRole)
                )
        );




    }

}
