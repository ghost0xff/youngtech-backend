package com.youngtechcr.www.profile;

import com.youngtechcr.www.security.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Transactional
    public Profile create(User user){
        Profile toBeCreated = Profile.builder().user(user).build();
        var created = profileRepository.save(toBeCreated);
        return created;
    }

    @Transactional
    public Profile create(Profile profile) {
        return profileRepository.save(profile);
    }

}
