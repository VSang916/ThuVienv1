package com.example.library_management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Thêm static import cho withDefaults()
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Tắt CSRF (nếu bạn không sử dụng form-based authentication)
            .authorizeRequests(auth -> auth
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Chỉ ADMIN mới truy cập các endpoint /api/admin/**
                .requestMatchers("/api/books/**").hasAnyRole("ADMIN", "USER") // ADMIN và USER có thể truy cập /api/books/**
                .requestMatchers("/api/authors/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/categories/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/readers/**").hasRole("ADMIN") // Chỉ ADMIN mới quản lý người đọc
                .requestMatchers("/api/borrowings/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated() // Các yêu cầu khác đều yêu cầu xác thực
            )
            .httpBasic(withDefaults()); // Sử dụng HTTP Basic Authentication với withDefaults()

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Sử dụng BCrypt để mã hóa mật khẩu
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService) // Sử dụng CustomUserDetailsService
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
}
