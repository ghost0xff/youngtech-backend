package com.youngtechcr.www.school.registration;

import com.youngtechcr.www.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolUnspecifiedRegistrationRepo extends
        JpaRepository<SchoolUnspecifiedRegistration, Integer> {

    Optional<SchoolUnspecifiedRegistration> findByUser(User user);

}
