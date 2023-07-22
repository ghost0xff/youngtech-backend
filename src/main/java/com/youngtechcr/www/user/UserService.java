package com.youngtechcr.www.user;

import com.youngtechcr.www.customer.CustomerService;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.AlreadyExistsException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.person.Person;
import com.youngtechcr.www.person.PersonService;
import com.youngtechcr.www.profile.Profile;
import com.youngtechcr.www.profile.ProfileService;
import com.youngtechcr.www.user.role.Role;
import com.youngtechcr.www.user.role.RoleOption;
import com.youngtechcr.www.user.role.RoleService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final BasicUserValidator basicUserValidator;
    private final UserRepository userRepository;
    private final CustomerService customerService;
    private final ProfileService profileService;
    private final PersonService personService;
    private final RoleService roleService;
    public UserService(
            PasswordEncoder passwordEncoder,
            BasicUserValidator basicUserValidator,
            UserRepository userRepository,
            CustomerService customerService,
            ProfileService profileService,
            PersonService personService, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.basicUserValidator = basicUserValidator;
        this.userRepository = userRepository;
        this.customerService = customerService;
        this.profileService = profileService;
        this.personService = personService;
        this.roleService = roleService;
    }

    @Transactional(readOnly = true)
    public User findById(Integer userId) {
        return this
                .userRepository
                .findById(userId)
                .orElseThrow(() -> new NoDataFoundException(
                        HttpErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND
                ));
    }


    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public User createUserFromBasicInfo(BasicUser basicUser) {
        if (!existsByEmail(basicUser.email())) {
            if (basicUserValidator.isValid(basicUser)) {
              User user = new User();
              String hashedPassword = passwordEncoder
                      .encode(basicUser.password());
              user.setPassword(hashedPassword);
              user.setEmail(basicUser.email());
              user.setSignedUpAt(LocalDateTime.now());
              user.setLastUpdateAt(LocalDateTime.now());
              Person relatedPerson = personService.createPersonFromUser(user);
              Profile relatedProfile = profileService.createProfileFromUser(user);
              addRoles(user, RoleOption.USER);
            }

        }
        throw new AlreadyExistsException(HttpErrorMessages
                    .INVALID_USER_REASON_ALREADY_EXISTS_EMAIL);
    }

    @Transactional
    public void addRoles(User user, RoleOption... roleOptions) {
        List<Role> rolesToBeAdded = roleService.mapOptionsToRoles(roleOptions);
        List<Role> currentRoles = user.getRoles();
        currentRoles.addAll(rolesToBeAdded);
        userRepository.save(user);
        return;
    }

    @Transactional(readOnly = true)
    private boolean existsByEmail(String email) {
        return  this.userRepository
                .existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsUserById(Integer userId) {
        return this.userRepository
                .existsById(userId);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

}
