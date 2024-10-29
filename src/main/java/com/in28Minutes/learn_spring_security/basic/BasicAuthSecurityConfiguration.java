package com.in28Minutes.learn_spring_security.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class BasicAuthSecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

       http.authorizeRequests(
               auth -> {
                   auth.anyRequest().authenticated();
               });

       http.sessionManagement(
               session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
       );

        http.httpBasic(withDefaults());

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

}