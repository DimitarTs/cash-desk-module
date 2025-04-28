package io.demo.cash_desk_module;

import io.demo.cash_desk_module.models.Cashier;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.io.File;

@Configuration
public class SecurityConfiguration {

    @Value("${api-key.header}")
    private String apiKeyHeader;
    @Value("${api-key.value}")
    private String apiKeyValue;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(request -> apiKeyValue.equals(request.getHeader(apiKeyHeader))).permitAll()
                )
                .build();
    }
}
