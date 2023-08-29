package com.youngtechcr.www.security.oidc;

import com.youngtechcr.www.security.CustomClaims;
import com.youngtechcr.www.security.user.User;
import com.youngtechcr.www.security.user.UserService;
import com.youngtechcr.www.security.user.role.Role;
import com.youngtechcr.www.utils.DsaUtils;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OidcUserInfoService {

    private final UserService userService;

    public OidcUserInfoService(UserService userService) {
        this.userService = userService;
    }

    public OidcUserInfo loadUserById(int id) {
        User user = userService.findById(id);
        Set<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet());
        OidcUserInfo info = OidcUserInfo.builder()
            .subject(String.valueOf(user.getId()))
            .email(user.getEmail())
            .emailVerified(user.isEmailVerified())
            .givenName(user.getPerson().getFirstnames()) // lastname
            .familyName(user.getPerson().getLastnames()) // lastnames
            .updatedAt(user.getLastUpdateAt().toString())
//            .claim("sid", "none")
            .claim(CustomClaims.ROLES, roles)
            .build();
        return info;

        /*
        * TODO: add all of the above :v
        * */
//        builder.address(null);
//        builder.birthdate(null);
//        builder.address(null);
//        builder.profile(null);
//        builder.website(null)
//        builder.locale(null);
//        builder.gender(null);
//        builder.middlename(middleName);
//        builder.name(middleName);
//        builder.nickname(middleName);
//        builder.picture(middleName);
//        builder.phoneNumber(middleName);
//        builder.phoneNumberVerified(middleName);
//        builder.preferredUsername(middleName);
//        return null;
    }

}
