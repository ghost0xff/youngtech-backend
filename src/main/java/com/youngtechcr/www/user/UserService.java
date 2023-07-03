package com.youngtechcr.www.user;

import com.youngtechcr.www.customer.CustomerService;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.AlreadyExistsException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.person.Person;
import com.youngtechcr.www.person.PersonService;
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
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final PersonService personService;
    private final CustomerService customerService;

    public UserService(
            PasswordEncoder passwordEncoder,
            UserValidator userValidator,
            UserRepository userRepository,
            PersonService personService, CustomerService customerService) {
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
        this.userRepository = userRepository;
        this.personService = personService;
        this.customerService = customerService;
    }

    @Transactional(readOnly = true)
    public User findById(Integer userId) {
        return this
                .userRepository
                .findById(userId)
                .orElseThrow( () -> new NoDataFoundException(
                        HttpErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND
                ));
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public User createBasicUserAndPerson(User userToBeCreated, Person relatedPerson) {
        if(!existsUserByUsernameAndEmail(userToBeCreated)) {
            if (this.userValidator.isValid(userToBeCreated)) {
                String hashedPasssword = this
                        .passwordEncoder.encode(userToBeCreated.getPassword());
                userToBeCreated.setPassword(hashedPasssword);
                userToBeCreated.setSignedUpAt(LocalDateTime.now());
                userToBeCreated.setLastUpdateAt(LocalDateTime.now());
                User createdUser = this.userRepository.save(userToBeCreated);
                Person createdPerson = personService.createPersonFromUser(
                        relatedPerson,createdUser);

                return createdUser;
            }
        }
        throw new AlreadyExistsException(
                HttpErrorMessages.CANT_CREATE_DUPLICATE_USER_REASON_USERNAME_OR_EMAIL
        );
    }


    private void createEntitiesFromUserTypes(User user,List<UserType> userTypes) {
        for (UserType type : userTypes){
            if(type == UserType.CUSTOMER) {
//                Customer customer = this.customerService.createCustomerFromUser();
            }
        }
    }

    private void addRolesFromUserTypes(User userWithoutRoles, List<UserType> types) {

        for (UserType type : types) {

        }

    }

    @Transactional(readOnly = true)
    private boolean existsUserByUsernameAndEmail(User userToBeInspected) {
        return  this.userRepository
                .existsByUsername(userToBeInspected.getUsername())
                &&
                this.userRepository
                .existsByEmail(userToBeInspected.getEmail());
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
