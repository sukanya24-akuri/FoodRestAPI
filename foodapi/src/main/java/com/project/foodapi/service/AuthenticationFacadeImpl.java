package com.project.foodapi.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade
{

    @Override
    public Authentication getauthentication()
    {
       return SecurityContextHolder.getContext().getAuthentication();
    }
}
