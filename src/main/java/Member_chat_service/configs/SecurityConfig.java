package Member_chat_service.configs;


import Member_chat_service.configs.jwt.CustomJwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity   
// @PreAuthorize("hasAuthority('ADMIN)") 관리자만 허용 메서드
public class SecurityConfig {

    @Autowired
    private CustomJwtFilter customJwtFilter; // 커스텀 필터

    @Autowired
    private CorsFilter corsFilter; // CORS 필터

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
                // csrf 코인 제거 jwt 코인을 사용하기 위해서
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                // 세션 사용불가 코드


        http.exceptionHandling(c -> { //인가 실패시
            c.authenticationEntryPoint((req,res,e)->{
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED); // 401 토큰이 없을때
            });

            c.accessDeniedHandler((req,res, e) -> {
                res.sendError(HttpServletResponse.SC_FORBIDDEN); // 403 권한이 없을때
            });
        });

        http.authorizeHttpRequests(c -> {    // 인가 주소 확인
            c.requestMatchers(
                   "/api/v1/member", // 회원가입시 사용
                   "/api/v1/member/token"/*,*/ // 로그인시 사용
                   /*"/api/v1/member/exists/**"*/).permitAll() // 이메일 인증때 사용
                    .anyRequest().authenticated(); // 나머지 URL 은 모두 회원 인증(토큰 인증) 필요함!!
        });



        // 시큐리티 무력화
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}