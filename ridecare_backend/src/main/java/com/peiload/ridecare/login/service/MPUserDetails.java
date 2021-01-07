package com.peiload.ridecare.login.service;

import com.peiload.ridecare.user.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public class MPUserDetails implements Serializable, org.springframework.security.core.userdetails.UserDetails {

    private User user;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public MPUserDetails(User user){
        this.user = user;
    }

    public MPUserDetails(User user, Collection<? extends GrantedAuthority> grantedAuthorities){
        this.grantedAuthorities = grantedAuthorities;
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return grantedAuthorities; }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
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
}
