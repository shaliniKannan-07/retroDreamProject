package com.cognizant.loginservice.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class AppUser implements UserDetails {
    private User user;
    private Collection<? extends GrantedAuthority> authorities;
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public AppUser(User user) {
        this.user = user;
        ArrayList<String> user_role = new ArrayList();
        user_role.add(user.getRole());
        this.authorities = user_role.stream().map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }
}
