package com.example.employez.security;

import com.example.employez.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {


    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) throws Exception {
        return new ProviderManager(daoAuthenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
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
                .authorizeHttpRequests().requestMatchers("/api/jobposts/byid/**").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/employee/login").permitAll()
                .anyRequest()
                .authenticated()
                .and().formLogin()
                /*.loginPage("/login-test")*/
                .loginProcessingUrl("/handleLogin")
                .defaultSuccessUrl("/homepage")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .deleteCookies("JSESSIONID").permitAll()
                .and().build();

    }


}
