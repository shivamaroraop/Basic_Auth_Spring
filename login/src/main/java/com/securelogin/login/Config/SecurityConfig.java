package com.securelogin.login.Config;

import com.securelogin.login.Services.AuthServices.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth=
                http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return auth.build();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     http
             .csrf(AbstractHttpConfigurer::disable)
             .authorizeHttpRequests(auth->auth
                     .requestMatchers("/api/v1/signup/1").permitAll()
                     .requestMatchers("/api/v1/login/1").permitAll()
                     .requestMatchers("/api/v1/me").authenticated()
                     .anyRequest().authenticated()
             )
             .sessionManagement(session -> session
                     .maximumSessions(1)
                     .maxSessionsPreventsLogin(false)
             )
             .logout(logout -> logout
                     .logoutUrl("/api/v1/logout")
                     .logoutSuccessHandler((request, response, authentication) -> {
                         response.setStatus(200);
                         response.setContentType("application/json");
                         response.getWriter().write("{\"success\":true,\"message\":\"Logout successful\"}");
                     })
                     .invalidateHttpSession(true)
                     .deleteCookies("JSESSIONID")
             );
     return http.build();
    }
}
