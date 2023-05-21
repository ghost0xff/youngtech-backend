package com.youngtechcr.www.repositories;

import com.youngtechcr.www.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
