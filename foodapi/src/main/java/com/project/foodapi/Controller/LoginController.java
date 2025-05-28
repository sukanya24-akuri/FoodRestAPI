package com.project.foodapi.Controller;

import com.project.foodapi.Util.JWTToken;
import com.project.foodapi.entity.LoginEntity;
import com.project.foodapi.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController
{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LoginService service;
    @Autowired
    private JWTToken token;


    @PostMapping("/login")
    public ResponseEntity<?> loginuser(@RequestBody LoginEntity request)
    {
        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPwd()));
           UserDetails userDetails= service.loadUserByUsername(request.getEmail());
           String jwtToken= token.generateToken(userDetails);
            LoginEntity response = new LoginEntity();
          response.setEmail(request.getEmail());
           response.setToken(jwtToken);
            response.setPwd(request.getPwd());
           return  new ResponseEntity<>(response,HttpStatus.OK);

        }
        catch (BadCredentialsException e)
        {
           return new ResponseEntity<>("incorrect email & password", HttpStatus.UNAUTHORIZED);
        }
        catch (Exception ee)
        {
          return new ResponseEntity<>(ee.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
