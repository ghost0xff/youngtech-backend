package com.youngtechcr.www.security.user;

import com.youngtechcr.www.customer.CustomerService;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.person.Person;
import com.youngtechcr.www.person.PersonService;
import com.youngtechcr.www.profile.Profile;
import com.youngtechcr.www.profile.ProfileService;
import com.youngtechcr.www.security.idp.IdentityProvider;
import com.youngtechcr.www.security.user.role.Role;
import com.youngtechcr.www.security.user.role.RoleOption;
import com.youngtechcr.www.security.user.role.RoleService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final BasicUserValidator basicUserValidator;
    private final UserRepository userRepository;
    private final CustomerService customerService;
    private final ProfileService profileService;
    private final PersonService personService;
    private final RoleService roleService;
    public UserService(
            BasicUserValidator basicUserValidator,
            UserRepository userRepository,
            CustomerService customerService,
            ProfileService profileService,
            PersonService personService, RoleService roleService
    ) {
//        this.passwordEncoder = passwordEncoder;
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


    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public User createFromEidteExchange(
            OidcIdToken token,
            IdentityProvider provider
    ) {
       String externalIdentifier = token.getSubject();
       return this
               .userRepository
               .findByIdpExternalIdentifier(externalIdentifier)
               .orElseGet( () -> {

                   // #1 Get some basic roles for the mf, just
                   List<Role> basicRoles = roleService.mapOptionsToRoles(
                           RoleOption.USER,
                           RoleOption.CUSTOMER
                   );
                   // #2 Create user object with all desired attributes
                   User.Builder userBuilder = User
                           .builder()
                           .identityProvider(provider)
                           .idpExternalIdentifier(externalIdentifier)
                           .signedUpAt(LocalDateTime.now())
                           .lastUpdateAt(LocalDateTime.now())
                           .email(token.getEmail())
                           .roles(basicRoles);

                   // #3 persist some user info
                  User createdUser = userRepository.save(userBuilder.build());

                   // #4 Build person based on claims and recently created user
                   var newPerson = Person
                           .builder()
                           .user(createdUser)
                           .firstnames(token.getGivenName())
                           .lastnames(token.getFamilyName())
                           .build();

                   // #5 Construct profile using image provided by the identity provider
                   var newProfile = Profile
                           .builder()
                           .user(createdUser)
                           .build();

                   this.personService.create(createdUser);
                   this.profileService.create(createdUser);
                   return createdUser;
               });
    }

}
