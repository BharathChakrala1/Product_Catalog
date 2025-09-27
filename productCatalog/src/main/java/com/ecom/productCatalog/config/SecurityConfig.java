package com.ecom.productCatalog.config;

import com.ecom.productCatalog.filter.JwtFilter;
import com.ecom.productCatalog.service.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
    private CustomUserDetailsServiceImpl userDetailsService;
	
	@Autowired
    private JwtFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.requestMatchers("/", "/login", "/register","/api/login", "/css/", "/js/", "/images/")
        		.permitAll().requestMatchers("/buyer/", "/cart/","/api/products/", "/api/categories/", "/api/users/").authenticated().anyRequest().authenticated())
                // Enable form login for UI users
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/buyer/home", true).permitAll()).logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll())
                // Use session for browser-based auth (UI)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                // Add JWT filter only for REST APIs
                /*.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)*/;
        return http.build();    
    }
}