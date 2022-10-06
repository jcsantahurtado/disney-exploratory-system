package com.juansanta.disney.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class Responsible for generating security
 * Class that implements the privileges of each user
 * UserDetails is a Spring Security class
 */
public class UserMain implements UserDetails {

    private String name;
    private String username;  // user
    private String email;
    private String password;
    // Variable that gives us the authorization (not to be confused with authentication)
    // Collection of generic type that extends
    // ...from GrantedAuthority from Spring security
    private Collection<? extends GrantedAuthority> authorities;

    // Constructor
    public UserMain(String name, String username, String email, String password, Collection<?
            extends GrantedAuthority> authorities) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // Method that assigns the privileges (authorization)
    public static UserMain build(User user){
        // Convert the Role class to the GrantedAuthority class
        List<GrantedAuthority> authorities =
                user.getUserRoleRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                        .collect(Collectors.toList());
        return new UserMain(user.getName(), user.getUsername(), user.getEmail(),
                user.getPassword(), authorities);
    }

    // @Override those with this annotation
    // means they are SpringSecurity UserDetails methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}