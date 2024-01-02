package com.greger.wigelltravels.securtiy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;


@Configuration
public class SecurityConfig {

        @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT username, password, active FROM customer WHERE username=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT username, authority FROM customer WHERE username=?");

        return jdbcUserDetailsManager;
    }



    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8585", "http://127.0.0.1:8585", "http://127.0.0.1:5500")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization")
                        .allowCredentials(true)
                        ;
            }
        };
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer

                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/username")).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/trips")).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/trips/{id}")).hasRole("USER")
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/v1/trips")).hasRole("USER")
                        .requestMatchers(mvc.pattern(HttpMethod.PUT, "/api/v1/trips/{id}")).hasRole("USER")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/customers")).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/v1/customers")).hasRole("ADMIN")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/customers/{id}")).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/api/v1/customers/{id}")).hasRole("ADMIN")
                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/api/v1/trips/{id}")).hasRole("ADMIN")
                        .requestMatchers(mvc.pattern(HttpMethod.PUT, "/api/v1/customers/{id}")).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/v1/destination")).hasRole("ADMIN")
                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/api/v1/destination/{id}")).hasRole("ADMIN")
                        .requestMatchers(mvc.pattern(HttpMethod.PUT, "/api/v1/destination/{id}")).hasRole("ADMIN")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/destination/{id}")).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/alltrips")).hasRole("ADMIN")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/currency/{total}/{currancy}")).hasAnyRole("USER", "ADMIN")
        );
        http.httpBasic(Customizer.withDefaults());
        http.cors(Customizer.withDefaults());
        http.csrf(csrf -> csrf
                .ignoringRequestMatchers(mvc.pattern("/h2-console/**"))
                .disable());
        http.headers().frameOptions().sameOrigin();
        return http.build();
    }
}
