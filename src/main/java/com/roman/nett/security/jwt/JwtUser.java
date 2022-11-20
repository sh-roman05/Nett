package com.roman.nett.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roman.nett.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Getter
@AllArgsConstructor
public class JwtUser implements UserDetails {

    @JsonIgnore private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    @JsonIgnore private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    @JsonIgnore private final Date lastPasswordResetDate;

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public User convertToUser() {
        return User.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build();

    }
}
