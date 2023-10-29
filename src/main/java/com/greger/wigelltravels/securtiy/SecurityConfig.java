package com.greger.wigelltravels.securtiy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/v1/trips").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/trips/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/trips").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/trips/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/customers/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/customers/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/destination").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/destination/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/destination/{id}").hasRole("ADMIN")
        );
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

//    @Bean
//    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspect) {
//        return new MvcRequestMatcher.Builder(introspect);
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
//        http.authorizeHttpRequests(configurer ->
//                configurer
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/trips")).hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/trips/{id}")).hasRole("USER")
//                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/v1/trips")).hasRole("USER")
//                        .requestMatchers(mvc.pattern(HttpMethod.PUT, "/api/v1/trips/{id}")).hasRole("USER")
////                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/trips")).hasRole("ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/customers")).hasRole("ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.PUT, "/api/v1/customers/{id}")).hasRole("ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/api/v1/customers/{id}")).hasRole("ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/v1/destination")).hasRole("ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.PUT, "/api/v1/destination/{id}")).hasRole("ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/api/v1/destination/{id}")).hasRole("ADMIN")
//        );
//        http.httpBasic(Customizer.withDefaults());
//        http.csrf(csrf -> csrf.disable());
//        return http.build();
//    }



}
