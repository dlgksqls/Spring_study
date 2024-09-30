package hello.spring_jwt.config;

import hello.spring_jwt.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    /**
     * 비밀번호 해쉬화 하기 위해ㅔㅐ
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // csrf disable
        // 세션방식에서는 세션이 항상 고정되기 때문에 csrf공격을 필수적으로 방어해줘야하지만
        // jwt에서는 세션이 stateless상태이기 때문에 안해줘도 됨
        http.csrf((auth)->auth.disable());

        // form 로그인 방식 disable
        http.formLogin((auth)-> auth.disable());

        // http basic 인증 방식 disable
        http.httpBasic((auth)->auth.disable());

        // 글로벌 인가 작업
        http.authorizeHttpRequests((auth)->auth
                .requestMatchers("/login", "/", "join").permitAll()
                .requestMatchers("admin").hasRole("ADMIN")
                .anyRequest().authenticated()); // 다른 요청에 대해서는 로그인 한 사용자만 접근 가능하도록

        // 필터 추가 LoginFilter()는 인자를 받음
        // (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)),
                UsernamePasswordAuthenticationFilter.class);

        /**
         * jwt에서는 세션을 stateless로 관리하게됨,, 중요!!!!!
         */
        http.sessionManagement((session)->session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
