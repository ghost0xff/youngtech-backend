package com.youngtechcr.www.repositories;

import com.youngtechcr.www.domain.UserAndRoleRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRelationshipRepository extends JpaRepository<UserAndRoleRelationship, Integer> {
}
