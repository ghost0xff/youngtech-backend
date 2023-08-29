package com.youngtechcr.www.person;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @RequestMapping(path = "/user/{id}")
    public Person findPersonByUserId(@PathVariable("id") Integer userId) {
        return personService.findByUser(userId);
    }

}
