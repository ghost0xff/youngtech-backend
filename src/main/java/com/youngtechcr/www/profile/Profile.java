package com.youngtechcr.www.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youngtechcr.www.profile.picture.ProfilePicture;
import com.youngtechcr.www.user.User;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="tbl_profile")
public class Profile {

    @Id
    @JsonIgnore
    private Integer profileId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "fk_id_user", referencedColumnName = "id_user")
    private User user;

    @OneToOne
    @JoinColumn(name = "fk_id_profile_picture", referencedColumnName = "id_profile_picture")
    private ProfilePicture profilePicture;

    public Profile(User user){
        this.user = user;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(user, profile.user) && Objects.equals(profilePicture, profile.profilePicture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, profilePicture);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "user=" + user +
                ", profilePicture=" + profilePicture +
                '}';
    }
}
