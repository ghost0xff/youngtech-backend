package com.youngtechcr.www.user;

import com.youngtechcr.www.customer.CustomerService;
import com.youngtechcr.www.person.PersonService;
import com.youngtechcr.www.profile.ProfileService;
import com.youngtechcr.www.user.role.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private BasicUserValidator basicUserValidator;
    private UserRepository userRepository;
    private CustomerService customerService;
    private ProfileService profileService;
    private PersonService personService;
    private RoleService roleService;
    @BeforeEach
    void setUp() {
        this.passwordEncoder = passwordEncoder;
        this.basicUserValidator = basicUserValidator;
        this.userRepository = userRepository;
        this.customerService = customerService;
        this.profileService = profileService;
        this.personService = personService;
//        this.roleService = new RoleService();
        userService = new UserService(
                passwordEncoder,
                basicUserValidator,
                userRepository,
                customerService,
                profileService,
                personService,
                roleService
        );
    }




}