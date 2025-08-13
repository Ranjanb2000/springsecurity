package com.learneasy.users.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csr -> csr.disable()).
                authorizeHttpRequests((requests) -> requests.
                requestMatchers("api/users/fetchAll").authenticated()
                .requestMatchers("/api/users/create").authenticated());
//                .requestMatchers(HttpMethod.POST,"api/users/create").authenticated());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }
//    @Bean
//    public UserDetailsService userDetailsService()
//    {
//        UserDetails user = User.withUsername("user").password("{noop}12345").authorities("read").build();
//        UserDetails user1 = User.withUsername("admin").password("{bcrypt}$2a$12$eoyQA90FpDMkFZDqGwI4PuPJyqkR63Vgjw7iVAAzOFD0paD6sLvNC").authorities("admin").build();
//        return new InMemoryUserDetailsManager(user,user1);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
