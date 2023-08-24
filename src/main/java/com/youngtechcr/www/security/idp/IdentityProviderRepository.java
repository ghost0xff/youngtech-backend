package com.youngtechcr.www.security.idp;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdentityProviderRepository extends JpaRepository<IdentityProvider, Integer> {

    Optional<IdentityProvider> findByName(String name);

}
