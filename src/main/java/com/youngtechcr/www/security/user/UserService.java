package com.youngtechcr.www.security.user;

import com.youngtechcr.www.customer.Customer;
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        Set<Role> rolesToBeAdded = roleService.mapOptionsToRoles(roleOptions);
        Set<Role> currentRoles = user.getRoles();
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


    /*
    * Add EMAIL only if id_token.emailVerified is TRUE
    * should have a flow that verifies user email
    * */
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

                   // #1 Get some basic roles for the mf user
                   Set<Role> basicRoles = roleService.mapOptionsToRoles(
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
                           .emailVerified(token.getEmailVerified())
                           .roles(basicRoles);

                   // #3 persist some user info
                  User createdUser = userRepository.save(userBuilder.build());

                   // #4 Build person based on claims and recently created user
                   var person = Person
                           .builder()
                           .user(createdUser)
                           .firstnames(token.getGivenName())
                           .lastnames(token.getFamilyName())
                           .build();

                   // #5 Construct profile using image provided by the identity provider
                   var profile = Profile
                           .builder()
                           .user(createdUser)
                           .build();

                   // #6 Persist data up to this moment
                   this.personService.create(person);
                   this.profileService.create(profile);

                   // #6 Build customer based on previously created objs and save
                   var customer =  new Customer();
                   customer.setCreatedAt(LocalDateTime.now());
                   customer.setUpdatedAt(LocalDateTime.now());
                   customer.setPerson(person);
                   customerService.create(customer);

                   return createdUser;
               });
    }

}
