package com.youngtechcr.www.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRelationshipRepository extends JpaRepository<UserAndRoleRelationship, Integer> {
}
