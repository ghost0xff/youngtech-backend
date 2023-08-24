package com.youngtechcr.www.profile;

import com.youngtechcr.www.security.user.User;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile create(User user){
        Profile toBeCreated = Profile.builder().user(user).build();
        var created = profileRepository.save(toBeCreated);
        return created;
    }

}
