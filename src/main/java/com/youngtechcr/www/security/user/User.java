package com.youngtechcr.www.security.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.youngtechcr.www.person.Person;
import com.youngtechcr.www.profile.Profile;
import com.youngtechcr.www.security.idp.IdentityProvider;
import com.youngtechcr.www.security.user.role.Role;
import jakarta.persistence.*;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "tbl_user")
@JsonDeserialize(builder = User.Builder.class)
public class User {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "fk_id_identity_provider", referencedColumnName = "id_identity_provider")
    private IdentityProvider identityProvider;
    @Column(name = "identity_provider_external_identifier")
    private String idpExternalIdentifier; // commonly the "sub" claim in an id_token


    private String email;
    @Column(name = "email_verified")
    private boolean emailVerified;

    @Column(name = "signed_up_at")
    private LocalDateTime signedUpAt;
    @Column(name = "last_update_at")
    private LocalDateTime lastUpdateAt;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbl_user_and_role",
            joinColumns = @JoinColumn(name = "fk_id_user", referencedColumnName = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "fk_id_role", referencedColumnName = "id_role")
    )
    private Set<Role> roles;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Person person;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Profile profile;

    public User() {
    }
    public User(Integer id){
        this.id = id;
    }

    private User(
            Integer id,
            IdentityProvider identityProvider,
            String idpExternalIdentifier,
            String email,
            boolean emailVerified,
            LocalDateTime signedUpAt,
            LocalDateTime lastUpdateAt,
            Set<Role> roles,
            Person person,
            Profile profile
    ) {
        this.id = id;
        this.identityProvider = identityProvider;
        this.idpExternalIdentifier = idpExternalIdentifier;
        this.email = email;
        this.signedUpAt = signedUpAt;
        this.lastUpdateAt = lastUpdateAt;
        this.roles = roles;
        this.person = person;
        this.profile = profile;
    }

    public User(String email, Collection<Role> roles) {
        this.email = email;
        this.roles = (Set<Role>) roles;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getSignedUpAt() {
        return signedUpAt;
    }

    public LocalDateTime getLastUpdateAt() {
        return lastUpdateAt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @JsonManagedReference("user-person")
    public Person getPerson() {
        return person;
    }


    @JsonManagedReference("user-profile")
    public Profile getProfile() {
        return profile;
    }


    @JsonManagedReference("user-identity_provider")
    public IdentityProvider getIdentityProvider() {
        return identityProvider;
    }

    public String getIdpExternalIdentifier() {
        return idpExternalIdentifier;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        if (emailVerified != user.emailVerified) return false;
        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(identityProvider, user.identityProvider))
            return false;
        if (!Objects.equals(idpExternalIdentifier, user.idpExternalIdentifier))
            return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(signedUpAt, user.signedUpAt)) return false;
        if (!Objects.equals(lastUpdateAt, user.lastUpdateAt)) return false;
        if (!Objects.equals(roles, user.roles)) return false;
        if (!Objects.equals(person, user.person)) return false;
        return Objects.equals(profile, user.profile);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (identityProvider != null ? identityProvider.hashCode() : 0);
        result = 31 * result + (idpExternalIdentifier != null ? idpExternalIdentifier.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (emailVerified ? 1 : 0);
        result = 31 * result + (signedUpAt != null ? signedUpAt.hashCode() : 0);
        result = 31 * result + (lastUpdateAt != null ? lastUpdateAt.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (person != null ? person.hashCode() : 0);
        result = 31 * result + (profile != null ? profile.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", idpExternalIdentifier='" + idpExternalIdentifier + '\'' +
                ", email='" + email + '\'' +
                ", emailVerified='" + emailVerified + '\'' +
                ", signedUpAt=" + signedUpAt +
                ", lastUpdateAt=" + lastUpdateAt +
                '}';
    }


   @JsonPOJOBuilder(withPrefix = "")
   static class Builder {

       private Integer id;
       private IdentityProvider identityProvider;
       private String idpExternalIdentifier; // commonly the "sub" claim in an id_token
       private String email;
       private boolean emailVerified;
       private LocalDateTime signedUpAt;
       private LocalDateTime lastUpdateAt;
       private Set<Role> roles;
       private Person person;
       private Profile profile;

       private Builder() { }

       public Builder id(Integer id) {
           this.id = id;
           return this;
       }
       public Builder identityProvider(IdentityProvider provider) {
           this.identityProvider = provider;
           return this;
       }

      public Builder idpExternalIdentifier(String idpExternalIdentifier) {
           this.idpExternalIdentifier = idpExternalIdentifier;
           return this;
      }

      public Builder email(String email) {
           this.email = email;
           return this;
      }
      public Builder emailVerified(boolean emailVerified) {
           this.emailVerified = emailVerified;
           return this;
       }

      public Builder signedUpAt(LocalDateTime signedUpAt) {
           this.signedUpAt = signedUpAt;
           return this;
      }

      public Builder lastUpdateAt(LocalDateTime lastUpdateAt) {
           this.lastUpdateAt = lastUpdateAt;
           return this;
      }

      public Builder role(Role role) {
           if(this.roles == null) {
               this.roles = new HashSet<>();
           }
           this.roles.add(role);
           return this;
      }

      /* TODO: Implement this but with Consumer<>
            obj so it's nicier to set roles
      */
      public Builder roles(Set<Role> roles) {
           this.roles = roles;
           return this;
      }

      public Builder person(Person person) {
          this.person = person;
          return this;
      }

      public Builder profile(Profile profile) {
          this.profile = profile;
          return this;
      }

      public User build() {
          Assert.notNull(this.identityProvider, "identity provider can't be null");
          Assert.notNull(
                  this.idpExternalIdentifier,
                  "identity provider external identifier can't be null");
          Assert.notNull(this.email, "email can't be null");
          Assert.notNull(this.signedUpAt, "signedUpAt can't be null");
          Assert.notNull(this.lastUpdateAt, "lastupdateAt can't be null");
          Assert.notNull(this.roles, "roles provider can't be null");

          return new User (
             this.id,
             this.identityProvider,
             this.idpExternalIdentifier,
             this.email,
             this.emailVerified,
             this.signedUpAt ,
             this.lastUpdateAt ,
             this.roles,
             this.person,
             this.profile
         );
      }
   }
}