package com.project.foodapi.Controller;

import com.project.foodapi.entity.RegisterEntity;
import com.project.foodapi.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
@CrossOrigin("*")
public class RegisterController
{
    @Autowired
    private IRegisterService service;
    @PostMapping("/register")
    public ResponseEntity<?> register( @RequestBody RegisterEntity entity)
    {
        try
        {
         RegisterEntity result=service.userRegister(entity);
         return new ResponseEntity<RegisterEntity>(result, HttpStatus.OK);

        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
