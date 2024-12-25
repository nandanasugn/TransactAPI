package com.nandana.transactapi.config;

import com.nandana.transactapi.util.AuthTokenFilter;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {
    private final AuthTokenFilter authTokenFilter;

    private static final String[] STRINGS = {"/registration/**", "/login/**", "/public/**", "/banner/**",};

    private static final String[] SWAGGER_WHITELIST = {"/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/swagger-resources",};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).httpBasic(AbstractHttpConfigurer::disable).formLogin(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, STRINGS).permitAll()).authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET, STRINGS).permitAll()).authorizeHttpRequests(authorize -> authorize.requestMatchers(SWAGGER_WHITELIST).permitAll()).authorizeHttpRequests(authorize -> authorize.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()).authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated()).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH").allowedHeaders("*").maxAge(3600);
    }
}
