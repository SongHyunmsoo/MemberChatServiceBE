package Member_chat_service.models.member;

import Member_chat_service.api.controllers.members.RequestLogin;
import Member_chat_service.configs.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor    // 토큰을가지고 정보를 만들어야하기에
public class MemberLoginService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * RequestLogin 레코드 클래스에 email password 가 있다.
     * @ form 양식이라 지키자
     * String 반환값은 스트링
     */
    public String login(RequestLogin form){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(form.email(),form.password());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 로그인 처리!!

        String accessToken = tokenProvider.createToken(authentication); // JWT 토큰 발급

        return accessToken;
    }
}
