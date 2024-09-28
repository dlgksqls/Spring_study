package hello.testsecurity.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

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

//        http.csrf((auth) -> auth.disable());

        // 서버가 시작되면 인증이 필요한 페이지에 대해 403오류 말고 로그인 페이지를 띄움
        http.formLogin((auth) -> auth.loginPage("/login")
                .loginProcessingUrl("/loginProc")
                .permitAll());

//        //다중 로그인 설정 (적용 안됨,,, 고민 좀 해보자,,,)
//        http.sessionManagement((auth) -> auth
//                .maximumSessions(1) // 하나의 아이디에 대한 다중 로그인 허용 갯수
//                .maxSessionsPreventsLogin(true)
//                .sessionRegistry(sessionRegistry()) // 다중 로그인 갯수를 초과했을 때 처리 방법 true : 로그인 차단, false : 기존 세션 하나 삭제
//                .expiredUrl("/login?expired"));

        // 세션 고정 보호
        http.sessionManagement((auth) -> auth
                .sessionFixation().changeSessionId());

        // 로그 아웃
        http.logout((auth) -> auth
                .logoutUrl("/logout") // 로그아웃 URL
                .logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트할 URL
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID") // 쿠키 삭제
        );

        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }// Register HttpSessionEventPublisher

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }
}
