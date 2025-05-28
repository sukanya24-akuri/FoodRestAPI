package com.project.foodapi.service;

import com.project.foodapi.entity.RegisterEntity;
import com.project.foodapi.repository.RegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterImpl implements IRegisterService
{
    @Autowired
    private RegisterRepo repo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationFacade authenticationFacade;


    @Override
    public RegisterEntity userRegister(RegisterEntity entity)
    {
        String encodedPassword = passwordEncoder.encode(entity.getPwd());
        entity.setPwd(encodedPassword);

     return  repo.save(entity);
    }

    @Override
    public String findByUserId()
    {
      String loggedUserMail=  authenticationFacade.getauthentication().getName();
      RegisterEntity loggeduser= repo.findByEmail(loggedUserMail).orElseThrow(()->new UsernameNotFoundException("id not found"));
      return loggeduser.getId();
    }
}

