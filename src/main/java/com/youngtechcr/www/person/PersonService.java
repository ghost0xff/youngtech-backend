package com.youngtechcr.www.person;

import com.youngtechcr.www.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public Person createPersonFromUser(User user) {
        Person toBeCreated = new Person(user);
        var created = personRepository.save(toBeCreated);
        return created;
    }

}
