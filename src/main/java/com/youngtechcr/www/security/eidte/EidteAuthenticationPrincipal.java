package com.youngtechcr.www.security.eidte;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.youngtechcr.www.security.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import javax.security.auth.Subject;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonDeserialize
public class EidteAuthenticationPrincipal implements Authentication {
    private Integer id;
    private String name;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean authenticated = false;

    public EidteAuthenticationPrincipal() { }

    public EidteAuthenticationPrincipal(
            User user,
            boolean authenticated
    ) {
        this(user);
        this.authenticated = authenticated;
    }

    public EidteAuthenticationPrincipal(
            User user
    ) {
        Assert.notNull(user);
        Assert.notNull(user.getId());
        Assert.notNull(user.getRoles());
        this.id = user.getId();
        this.authenticated = true;
        this.authorities = user
                .getRoles()
                .stream()
                .map(role -> {
                    Assert.notNull(role);
                    return new SimpleGrantedAuthority(role.getName());
                })
                .collect(Collectors.toList());
        this.name = user.getPerson() != null && user.getPerson().getFirstnames() != null ?
                user.getPerson().getFirstnames()
                :
                String.valueOf(user.getId());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    @JsonIgnore
    public Object getCredentials() {
        return null;
    }

    @Override
    @JsonIgnore
    public Object getDetails() {
        return null;
    }

    @Override
    @JsonIgnore
    public Object getPrincipal() {
        return this.id;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return String.valueOf(this.id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EidteAuthenticationPrincipal that = (EidteAuthenticationPrincipal) o;
        return authenticated == that.authenticated && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authorities, authenticated);
    }

    @Override
    public String toString() {
        return "EidteAuthenticationPrincipal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roles=" + authorities +
                ", authenticated=" + authenticated +
                '}';
    }
}