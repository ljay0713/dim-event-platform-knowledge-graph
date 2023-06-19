package com.sysco.event.platform.knowledge.graph.core;

import com.sysco.event.platform.knowledge.graph.core.security.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@SpringBootApplication(
                scanBasePackages = {
                                "com.sysco.event.platform.knowledge.graph.core",
                                "com.sysco.event.platform.knowledge.graph.confluent.cloud.client"
                }
)
public class KnowledgeGraphCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowledgeGraphCoreApplication.class, args);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                        .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                        .authorizeHttpRequests(auth -> auth
                                        .requestMatchers(HttpMethod.GET, "/token", "/error")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.POST, "/graphql")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated());
        return http.build();
    }
}
