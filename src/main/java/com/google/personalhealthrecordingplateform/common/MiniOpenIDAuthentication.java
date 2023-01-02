package com.google.personalhealthrecordingplateform.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/1/1 16:37
 */
public class MiniOpenIDAuthentication implements Authentication {
    private Object principal;
    private Object credentials;
    private String session_key;
    boolean isAuthenticated;

    public MiniOpenIDAuthentication(Object principal, Object credentials, String session_key) {
        this.principal = principal;
        this.credentials = credentials;
        this.session_key = session_key;
    }


    public String getSession_key() {
        return session_key;
    }

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
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }
}
