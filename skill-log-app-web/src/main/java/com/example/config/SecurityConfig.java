package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
            )
            .csrf(csrf -> {
                // Default CSRF protection stays enabled.
                // csrf.ignoringRequestMatchers("/h2-console/**"); // Example for H2 console access.
            });
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    /*
     * // Spring Boot 2.7.x / Spring Security 5.x example using WebSecurityConfigurerAdapter:
     * @Configuration
     * @EnableWebSecurity
     * public class SecurityConfig extends WebSecurityConfigurerAdapter {
     *     @Override
     *     protected void configure(HttpSecurity http) throws Exception {
     *         http
     *             .authorizeRequests()
     *                 .antMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
     *                 .anyRequest().authenticated()
     *             .and()
     *             .formLogin()
     *                 .loginPage("/login").permitAll()
     *                 .defaultSuccessUrl("/", true)
     *                 .failureUrl("/login?error")
     *             .and()
     *             .logout()
     *                 .logoutUrl("/logout")
     *                 .logoutSuccessUrl("/login?logout");
     *     }
     * }
     */
}
