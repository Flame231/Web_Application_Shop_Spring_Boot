package org.example.webApplicationShopSpringBoot.controller.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityFilterConfig {

    private final CustomSuccessHandler successHandler;

    @Value("${admin.path}")
    private String adminPath;

    @Value("${client.path}")
    private String clientPath;

    @Value("${operator.path}")
    private String operatorPath;

    public SecurityFilterConfig(CustomSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/registration").permitAll()
                        .requestMatchers(adminPath + "**").hasRole("ADMINISTRATOR")
                        .requestMatchers(clientPath + "**").hasRole("CLIENT")
                        .requestMatchers(operatorPath + "**").hasRole("OPERATOR")
                        .anyRequest().authenticated()).formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .successHandler(successHandler)
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        return http.build();
    }
}
