package com.youngtechcr.www.person;

import com.youngtechcr.www.security.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
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
