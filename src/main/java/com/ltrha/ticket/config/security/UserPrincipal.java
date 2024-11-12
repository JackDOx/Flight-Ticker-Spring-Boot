package com.ltrha.ticket.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ltrha.ticket.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private UUID id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private List<GrantedAuthority> authorities;
    private String token;

    private Map<String, Object> attributes;


    public static UserPrincipal build(UserEntity user) {
        if (user == null || user.getRole() == null) throw new RuntimeException("User not found");
        ;

        String role = user.getRole();

        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPhoneNumber(),
                List.of(authority),
                null,
                null
        );
    }

    public static UserPrincipal build(UserEntity user, String token) {
        if (user == null || user.getRole() == null) {
            throw new RuntimeException("User not found");
        }
        ;

        String role = user.getRole();

        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPhoneNumber(),
                List.of(authority),
                token,
                null
        );
    }

    public UserPrincipal(UUID id, String email) {
        this.id = id;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.authorities;

    }


    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }


}
