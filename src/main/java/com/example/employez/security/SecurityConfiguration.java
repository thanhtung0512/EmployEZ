package com.example.employez.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {


    // Authentication part
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails company = User
                .withUsername("company")
                .password(passwordEncoder.encode("company"))
                .roles("ADMIN").build();

        UserDetails employee = User
                .withUsername("user")
                .password(passwordEncoder.encode("abc"))
                .roles("USER").build();
        return new InMemoryUserDetailsManager(company, employee);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/css/**")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/img/**")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/js/**")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/scss/**")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/fonts/**")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/homepage").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/jobposts/byid/**").hasRole("ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers("/search").hasAnyRole("ADMIN").anyRequest()
                .authenticated()
                .and().formLogin()
                .and().build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
