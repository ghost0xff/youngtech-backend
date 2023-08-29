package com.youngtechcr.www.person;

import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.security.annotations.roles.CustomerRole;
import com.youngtechcr.www.security.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    private final PersonRepository personRepository;


    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @CustomerRole
    public Person findByUser(Integer userId) {
        User user = new User(userId);
        return  personRepository
                .findByUser(user)
                .orElseThrow(() -> {
                    return new NoDataFoundException(HttpErrorMessages
                            .NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
                });
    }

    @Transactional
    public Person create(User user) {
        Person toBeCreated = Person
                .builder()
                .user(user)
                .build();
        var created = personRepository.save(toBeCreated);
        return created;
    }

    @Transactional
    public Person create(Person person) {
        return personRepository.save(person);
    }

}
