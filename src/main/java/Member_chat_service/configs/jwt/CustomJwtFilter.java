package Member_chat_service.configs.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomJwtFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;

        /* 요청 헤더 Authoriztion 항목의 JWT 토큰 추출  S */
        String header = req.getHeader("Authorization");
        String jwt = null;
        if (StringUtils.hasText(header)) {
            jwt = header.substring(7);
            //Bear
        }|

        /* 요청 헤더 Authoriztion 항목의 JWT 토큰 추출  S */

        chain.doFilter(request, response);

    }
}