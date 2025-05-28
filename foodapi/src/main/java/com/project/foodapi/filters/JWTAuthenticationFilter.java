package com.project.foodapi.filters;

import com.project.foodapi.Util.JWTToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter
{

    @Autowired
    private JWTToken jwtToken;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

String header=request.getHeader("Authorization");
if(StringUtils.hasText(header) && header.startsWith("Bearer "))
{
    String token=header.substring(7);
    String email= jwtToken.extractUsername(token);


    if(email !=null && SecurityContextHolder.getContext().getAuthentication()==null)
    {
        UserDetails userDetails= userDetailsService.loadUserByUsername(email);

        if(jwtToken.validateToken(token,userDetails))
        {
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                    userDetails,null,userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
filterChain.doFilter(request,response);
    }
}
