package com.youngtechcr.www.security.user;

import com.youngtechcr.www.security.idp.IdentityProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
        boolean existsByEmail(String email);

        Optional<User> findByIdpExternalIdentifier(String idpExternalIdentifier);


}
