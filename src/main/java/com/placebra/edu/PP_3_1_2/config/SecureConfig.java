package com.placebra.edu.PP_3_1_2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecureConfig {

    private final UserDetailsService userDetailsService; // Inject обработчика User-ов

    public SecureConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSuccessHandler loginSuccessHandler) throws Exception {

        // ПРАВИЛА РАССМАТРИВАЮТСЯ СВЕРХУ ВНИЗ
        return http
            .formLogin(form -> form
                    .loginPage("/") // Где находится login страница
                    .loginProcessingUrl("/login") //Где ловить Post запрос от страницы
                    .successHandler(loginSuccessHandler)  // Подключается кастомный SuccessHandler
                    .permitAll())
            .logout(log -> log
                    .logoutSuccessUrl("/"))
            .csrf().disable()
            .authorizeHttpRequests(auth -> {    // Настройка Http запросов
            auth
                .requestMatchers("/admin", "/admin/**") //Если путь содержит /admin?count=50 или /admin/**
                .hasRole("ADMIN") // То проверь, он авторизован его роль Admin?
                .requestMatchers("/user", "/user/**") //Если путь содержит /user?id=2 или /user/**
                .hasAnyRole("USER", "ADMIN")
                .anyRequest() // На все другие url
                .permitAll(); // свободный доступ
            })

            .userDetailsService(userDetailsService) // Обработчик User-ов

            .build();     // строит объект SecurityFilterChain на основе настроек // КОНЕЦ
    }

    @Bean // Encoder для работы BCrypt
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
