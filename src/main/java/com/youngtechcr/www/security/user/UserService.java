package com.youngtechcr.www.security.user;

import com.youngtechcr.www.customer.Customer;
import com.youngtechcr.www.customer.CustomerService;
import com.youngtechcr.www.domain.TimestampedUtils;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.person.Person;
import com.youngtechcr.www.person.PersonService;
import com.youngtechcr.www.profile.Profile;
import com.youngtechcr.www.profile.ProfileService;
import com.youngtechcr.www.security.exceptions.UnkownTokenException;
import com.youngtechcr.www.security.idp.IdentityProvider;
import com.youngtechcr.www.security.user.role.Role;
import com.youngtechcr.www.security.user.role.RoleOption;
import com.youngtechcr.www.security.user.role.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class UserService {

    private final BasicUserValidator basicUserValidator;
    private final UserRepository userRepository;
    private final CustomerService customerService;
    private final ProfileService profileService;
    private final PersonService personService;
    private final RoleService roleService;
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
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


    @Transactional(readOnly = true)
    public  User toUser(Authentication authn) {
        int id = Integer.parseInt(authn.getName());
        return userRepository
                .findById(id)
                .orElseThrow(() -> {
                    logger.warn(
                            "Somehow received token with unkown user id, got hacked??" +
                            " or error on authz server signing tokens???" +
                            " or did user delete account/user data???");
                    return new UnkownTokenException(
                            "Heyy!! how do you have a signed token with and unvalid user id??? is this user deleted??");
                });
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
                   TimestampedUtils.setTimestampsToNow(customer);
                   customer.setUser(createdUser);
                   // save customer
                   customerService.create(customer);

                   return createdUser;
               });
    }

}
