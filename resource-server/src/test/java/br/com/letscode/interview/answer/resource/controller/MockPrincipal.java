package br.com.letscode.interview.answer.resource.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public class MockPrincipal implements Authentication {

    private String principal = null;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        if(principal == null){
            principal = UUID.randomUUID().toString();
        }
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
