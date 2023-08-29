package com.youngtechcr.www.person;

import com.youngtechcr.www.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUser(User user);
}
