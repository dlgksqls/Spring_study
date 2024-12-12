package io.secyruty.springsecuritymaster;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean // 사용자 정의 보안
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user2 = User
                .withUsername("user2")
                .password("{noop}1111")
                .roles("USER")
                .build();

        UserDetails user3 = User
                .withUsername("user3")
                .password("{noop}1111")
                .roles("USER")
                .build();

        UserDetails user4 = User
                .withUsername("user4")
                .password("{noop}1111")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user2, user3, user4);
    }
}
