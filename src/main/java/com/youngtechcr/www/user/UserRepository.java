package com.youngtechcr.www.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
        boolean existsByUsername(String username);
        boolean existsByEmail(String email);

}
