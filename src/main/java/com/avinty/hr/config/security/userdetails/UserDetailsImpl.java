package com.avinty.hr.config.security.userdetails;

import com.avinty.hr.modules.employee.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * UserDetails implementation based on Employee entity for authentication
 * @author mredly
 */
@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    //Represents Employee's email address
    private String username;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    // Convert an employee to an authentication user
    public static UserDetailsImpl build(Employee employee) {
        GrantedAuthority authority = new SimpleGrantedAuthority(employee.getRole().name());
        return new UserDetailsImpl(employee.getEmail(), employee.getPassword(), Collections.singletonList(authority));
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
}
