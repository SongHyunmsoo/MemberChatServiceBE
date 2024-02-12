package Member_chat_service.configs.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@EnableConfigurationProperties(JwtProperties.class)
public class TokenProvider {

    @Autowired
    private JwtProperties jwtProperties;
    private final String secret; // 민감한 데이터 파이널로 보호
    private final Long toekValidityInSeconds;   // 민감한 데이터 파이널로 보호

    private Key key;

    public TokenProvider(){ // HMAC -> SHA512 + Message
        secret = jwtProperties.getSecret();
        toekValidityInSeconds = jwtProperties.getAccessTokenValidityInSeconds();

        byte[] bytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(Authentication authentication) {

        return null;
    }
}
