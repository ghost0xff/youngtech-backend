package com.youngtechcr.www.profile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.youngtechcr.www.profile.picture.ProfilePicture;
import com.youngtechcr.www.security.user.User;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="tbl_profile")
@JsonDeserialize(builder = Profile.Builder.class)
public class Profile {

    @Id
    @JsonIgnore
    private Integer id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "fk_id_user", referencedColumnName = "id_user")
    private User user;

    @OneToOne
    @JoinColumn(name = "fk_id_profile_picture", referencedColumnName = "id_profile_picture")
    private ProfilePicture profilePicture;

    public Profile() { }

    private Profile(Integer id, User user, ProfilePicture profilePicture) {
        this.id = id;
        this.user = user;
        this.profilePicture = profilePicture;
    }

    public Integer getId() {
        return id;
    }

    @JsonBackReference("user-profile")
    public User getUser() {
        return user;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        if (!Objects.equals(id, profile.id)) return false;
        if (!Objects.equals(user, profile.user)) return false;
        return Objects.equals(profilePicture, profile.profilePicture);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (profilePicture != null ? profilePicture.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private Integer id;
        private User user;
        private ProfilePicture profilePicture;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder profilePicture(ProfilePicture profilePicture) {
            this.profilePicture = profilePicture;
            return this;
        }

        public Profile build() {
            return new Profile(
                this.id,
                this.user,
                this.profilePicture
            );
        }
    }
}
