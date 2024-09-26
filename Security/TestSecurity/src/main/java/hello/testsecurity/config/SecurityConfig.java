package hello.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 이 클래스가 스프링 시큐리티 안에서도 관리가 됨
public class SecurityConfig {

    /**
     * 비밀번호 단방향 암호화(=해쉬)
     * 단방향 암호는 한쪽에서는 해독이 불가능
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/","join","/login", "/loginProc", "/joinProc").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated());

        http.csrf((auth) -> auth.disable());

        // 서버가 시작되면 인증이 필요한 페이지에 대해 403오류 말고 로그인 페이지를 띄움
        http.formLogin((auth) -> auth.loginPage("/login")
                .loginProcessingUrl("/loginProc")
                .permitAll());

        return http.build();
    }
}
