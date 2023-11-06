package com.bookUniverse.BookUniverse.model.login;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUserDetails implements UserDetails {
    private String name;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails(User user){
        this.name = user.getUsername();
        this.password = user.getPassword();
        this.authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRole().name())).collect(Collectors.toList());
    }

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
        return name;
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
