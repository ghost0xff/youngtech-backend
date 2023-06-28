package com.youngtechcr.www.user;

import com.youngtechcr.www.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
