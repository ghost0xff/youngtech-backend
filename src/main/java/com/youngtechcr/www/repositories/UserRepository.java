package com.youngtechcr.www.repositories;

import com.youngtechcr.www.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
