package com.NSU.BookingSpecialist.config;

import com.NSU.BookingSpecialist.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService(UserDetailsServiceImpl impl) {
//        return impl;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth.html", "/main.html", "/**.html",
                                "/login", "/logout",
                                "/api/auth/signup", "/api/auth/login",
                                "/api/appointments/**",
                                "/api/specialists/**",
                                "/static/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/main.html", true)
                        .failureUrl("/auth.html?error=true")
                        .permitAll()
                )
                .logout(logout -> logout.logoutUrl("/logout").permitAll());


        return http.build();
    }
}
