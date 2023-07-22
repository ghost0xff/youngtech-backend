package com.youngtechcr.www.profile;

import com.youngtechcr.www.user.User;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile createProfileFromUser(User user){
        Profile toBeCreated = new Profile(user);
        var created = profileRepository.save(toBeCreated);
        return created;
    }

}
