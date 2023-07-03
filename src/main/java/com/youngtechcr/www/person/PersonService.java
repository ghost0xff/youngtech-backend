package com.youngtechcr.www.person;

import com.youngtechcr.www.user.User;
import com.youngtechcr.www.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final UserService userService;

    public PersonService(PersonRepository personRepository, UserService userService) {
        this.personRepository = personRepository;
        this.userService = userService;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Person createPersonFromUser(Person personToBeCreated, User existingUser) {
        if(userService.existsUserById(existingUser.getUserId())) {
            personToBeCreated.setUser(existingUser);
            Person createdPersonFromUser = this.personRepository.save(personToBeCreated);
        }

        return null;
    }
}
