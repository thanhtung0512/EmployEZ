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
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {


    // Authentication part
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails company = User
                .withUsername("company@gmail.com")
                .password(passwordEncoder.encode("company@gmail.com"))
                .roles("ADMIN").build();

        UserDetails employee = User
                .withUsername("user@gmail.com")
                .password(passwordEncoder.encode("user@gmail.com"))
                .roles("USER").build();
        return new InMemoryUserDetailsManager(company, employee);
    }


    public LogoutSuccessHandler logoutSuccessHandler() {
        return new SimpleUrlLogoutSuccessHandler();
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
                .authorizeHttpRequests().requestMatchers("/search").hasAnyRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and().formLogin()
                /*.loginPage("/employee/login")*/
                /*.loginProcessingUrl("/employeeHandleLogin")*/
                .defaultSuccessUrl("/homepage", true)
                .failureUrl("/login?error=true").permitAll()
                /*.failureHandler(authenticationFailureHandler())*/
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .deleteCookies("JSESSIONID").permitAll()
                /*.logoutSuccessHandler(logoutSuccessHandler())*/.and().build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
