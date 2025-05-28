package com.project.foodapi.service;

import com.project.foodapi.entity.RegisterEntity;
import com.project.foodapi.repository.RegisterRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class LoginService implements UserDetailsService
{
    @Autowired
    private RegisterRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        RegisterEntity user=repo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException(("user not found")));
       return  new User( user.getEmail(),user.getPwd(), Collections.emptyList());

    }


}

