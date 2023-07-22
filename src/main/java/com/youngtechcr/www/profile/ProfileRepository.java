package com.youngtechcr.www.profile;

import com.youngtechcr.www.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
