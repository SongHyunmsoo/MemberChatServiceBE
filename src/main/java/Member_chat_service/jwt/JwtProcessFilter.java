package Member_chat_service.jwt;

import Member_chat_service.member.MemberUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

// 필터 클래스
@Component
@RequiredArgsConstructor

public class JwtProcessFilter extends GenericFilterBean {


    private final MemberUtil memberUtil;
    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = memberUtil.getToken();
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }


}
/*


@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;

        */
/* 요청 헤더 Authoriztion 항목의 JWT 토큰 추출  S *//*

        String header = req.getHeader("Authorization");
        String jwt = null;
        if (StringUtils.hasText(header)) {
            //  StringUtils 에서 hasText 코드가 널값 체크 + 공백 체크
            jwt = header.substring(7);
            //Bearer.... 토큰 Bearer명칭
        }
        */
/* 요청 헤더 Authoriztion 항목의 JWT 토큰 추출 E *//*


        */
/* 로그인 유지 처리 S *//*

        if (StringUtils.hasText(jwt)) {
            tokenProvider.validateToken(jwt); // 토큰 이상시 -> 예외 발생

            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);   // 성공시 로그인 유지 코드
        }
        */
/* 로그인 유지 처리 E *//*


        chain.doFilter(request, response);
    }
}*/
