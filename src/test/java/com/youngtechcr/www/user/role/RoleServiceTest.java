package com.youngtechcr.www.user.role;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

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
    @DisplayName("Given existing role should return true")
    void given_Existin_Role_Should_Return_True() {

        //Preparing assertion
        List<Role> existingRoles = new ArrayList<>();
        existingRoles.add(new Role(2));

        //Preparing 'when's
        when(this.roleRepository.existsById(2)).thenReturn(true);

        //Assertions
        Assertions.assertTrue(this.roleService.doThisRolesExist(existingRoles));

    }

    @Test
    @DisplayName("Given ONE NONE EXISTING role should return false")
    void given_One_Unexisting_Role_Should_Return_False() {

        //Preparing assertion
        List<Role> existingRoles = new ArrayList<>();
        existingRoles.add(new Role(10));

        //Preparing 'when's
        when(this.roleRepository.existsById(410)).thenReturn(false);

        //Assertions
        Assertions.assertFalse(this.roleService.doThisRolesExist(existingRoles));

    }


    @Test
    @DisplayName("Given some NONE existing and some existing role should return false")
    void given_Somee_Unexisting_Roles_And_Some_Existing_Roles_Should_Return_False() {

        //Preparing assertion
        List<Role> existingRoles = new ArrayList<>();
        existingRoles.add(new Role(1));
        existingRoles.add(new Role(2));
        existingRoles.add(new Role(13));
        existingRoles.add(new Role(15));
        existingRoles.add(new Role(20));

        //Preparing 'when's
        when(this.roleRepository.existsById(1)).thenReturn(true);
        when(this.roleRepository.existsById(2)).thenReturn(true);
        when(this.roleRepository.existsById(13)).thenReturn(false);
        when(this.roleRepository.existsById(15)).thenReturn(false);
        when(this.roleRepository.existsById(20)).thenReturn(false);

        //Assertions
        Assertions.assertFalse(this.roleService.doThisRolesExist(existingRoles));

    }





}