package com.example.ECommercePlatform.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .headers(headers -> headers.cacheControl(HeadersConfigurer.CacheControlConfig::disable))
                .httpBasic(Customizer.withDefaults())
                .userDetailsService(userDetailsService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,
                                "/user/signUp", "/user/logIn" ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/create-product", "/admin/create-category" ).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/user/create-review", "/user/add-to-cart", "/user/create-order", "/all").hasAuthority("USER")
                        .requestMatchers(HttpMethod.PUT, "/admin/update-product").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/user/remove-from-cart").hasAuthority("USER")
                        .requestMatchers(HttpMethod.DELETE, "/admin/delete-product").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/product-search").hasAuthority("USER")
                        .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
                )

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}


