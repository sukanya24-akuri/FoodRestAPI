package com.project.foodapi.config;


import com.project.foodapi.filters.JWTAuthenticationFilter;
import com.project.foodapi.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Autowired
    private LoginService login;
    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws  Exception
    {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/login","/api/register","/api/food/**","/api/order/all","/api/order/status/**,/api/cart/delete/item")
                        .permitAll().anyRequest().authenticated())
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

   @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


@Bean
public  CorsFilter corsFilter()
{
    return  new CorsFilter(corsConfigurationSource());
}

@Bean
public UrlBasedCorsConfigurationSource corsConfigurationSource()
{
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("https://food-adminpanel.netlify.app", "https://more-quantitynqualityfoods.netlify.app"));
    config.setAllowedMethods(List.of("POST", "PUT", "PATCH", "DELETE", "GET"));
    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
return source;

}
@Bean
public AuthenticationManager authenticationManager()
{
    DaoAuthenticationProvider daoauth=new DaoAuthenticationProvider();
    daoauth.setUserDetailsService(login);
    daoauth.setPasswordEncoder(passwordEncoder());
    return  new ProviderManager(daoauth);

}
}
