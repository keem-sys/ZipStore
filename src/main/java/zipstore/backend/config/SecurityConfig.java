package zipstore.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // standard for REST APIs
                .authorizeHttpRequests(auth -> auth
                        // Allow "GET" requests to /api/products and anything after it
                        .requestMatchers("/api/products/**").permitAll()
                        // Lock everything else
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}