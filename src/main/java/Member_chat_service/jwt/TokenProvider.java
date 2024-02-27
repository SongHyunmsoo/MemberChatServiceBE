package Member_chat_service.jwt;

import Member_chat_service.member.constants.Authority;
import Member_chat_service.member.service.MemberInfo;
import Member_chat_service.member.service.MemberInfoService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;




@Component
@EnableConfigurationProperties(JwtProperties.class)
public class TokenProvider {

    private MemberInfoService memberInfoService;

    private JwtProperties props;

    private Key key;

    public TokenProvider(MemberInfoService memberInfoService, JwtProperties props) {
        this.memberInfoService = memberInfoService;
        this.props = props;

        byte[] keyBytes = Decoders.BASE64.decode(props.getSecret());
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) { // 토큰 발급 메서드 토큰안에 로그인 정보
        String authorities = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); // 권한 관련 코드

        long now = (new Date()).getTime(); // EpochTime
        Date validity = new Date(now + props.getAccessTokenValidityInSeconds() * 1000);
        // 현재시간 에서 1시간 까지만 유지

        return Jwts.builder()
                .setSubject(authentication.getName()) // 아이디
                .claim("auth", authorities) // 권한
                .signWith(key, SignatureAlgorithm.HS512) // HMAC + SHA512  HS512: 한번더 검증 하는코드
                .setExpiration(validity) // 토큰 유효시간
                .compact();
    }


    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser() // 토큰 정보를 받아서 분해 해석하는 코드
                .setSigningKey(key) // 검증 하는역활
                .build() // 토큰은 감싸주는 역활
                .parseClaimsJws(token) // 토큰안에 정보가 저장되어있다.
                .getPayload(); // 조회코드

        String _authorities = (String)claims.get("auth");
        _authorities = StringUtils.hasText(_authorities) ? _authorities : Authority.USER.name();

        List<SimpleGrantedAuthority> authorities = Arrays.stream(_authorities.split(",")).map(SimpleGrantedAuthority::new).toList();

        MemberInfo memberInfo = (MemberInfo)memberInfoService.loadUserByUsername(claims.getSubject());
        memberInfo.setAuthorities(authorities);

        Authentication authentication = new UsernamePasswordAuthenticationToken(memberInfo, token, authorities);

        return authentication;
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser() // 토큰 정보를 받아서 분해 해석하는 코드
                    .setSigningKey(key) // 검증 하는역활
                    .build()  // 토큰은 감싸주는 역활
                    .parseClaimsJws(token) // 토큰안에 정보가 저장되어있다.
                    .getBody(); // 조회코드
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

/*


private final String secret; // 민감한 데이터 파이널로 보호
    private final long tokenValidityInSeconds;   // 민감한 데이터 파이널로 보호

    @Autowired
    private MemberInfoService infoService;

    private Key key;

    public TokenProvider(String secret, Long tokenValidityInSeconds) {
        // HMAC -> SHA512 + Message
        this.secret = secret;
        this.tokenValidityInSeconds = tokenValidityInSeconds;

        byte[] bytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(Authentication authentication) { // 토큰 발급 메서드 토큰안에 로그인 정보
        String authories = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); // 권한 관련 코드

        Date expires = new Date((new Date()).getTime() + tokenValidityInSeconds * 1000);
            // 현재시간 에서 1시간 까지만 유지

        return Jwts.builder()
                .setSubject(authentication.getName())// 아이디
                .claim("auth", authories)
                .signWith(key, SignatureAlgorithm.HS512)
                // HMAC + SHA512
                // HS512: 한번더 검증 하는코드
                .setExpiration(expires)    // 토큰 유효시간
                .compact();
    }
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser() // 토큰 정보를 받아서 분해 해석하는 코드
                .setSigningKey(key)
                // 검증 하는역활
                .build()
                // 토큰은 감싸주는 역활
                .parseClaimsJws(token)
                // 토큰안에 정보가 저장되어있다.
                .getPayload();
                // 조회코드
        String email = claims.getSubject(); // 이메일 정보를 조회
        MemberInfo userDetails = (MemberInfo)infoService.loadUserByUsername(email);

        String auth = claims.get("auth").toString();
        List<? extends GrantedAuthority> authorities = Arrays.stream(auth.split(","))
                .map(SimpleGrantedAuthority::new).toList();
        userDetails.setAuthorities(authorities);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, token, authorities);

        return authentication;
    }

    public void validateToken(String token) {
        try {
            Claims claims = Jwts.parser() // 토큰 정보를 받아서 분해 해석하는 코드
                    .setSigningKey(key)
                    // 검증 하는역활
                    .build()
                    // 토큰은 감싸주는 역활
                    .parseClaimsJws(token)
                    // 토큰안에 정보가 저장되어있다.
                    .getPayload();
                    // 조회코드


        } catch (ExpiredJwtException e) { // 만료된 토큰
            throw new BadRequestException(Utils.getMessage("EXPIRED.JWT_TOKEN", "validation"));

        } catch (UnsupportedJwtException e) { // 지원되지 않는 토큰
            throw new BadRequestException(Utils.getMessage("UNSUPPORTED.JWT_TOKEN", "validation"));
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException e) {// 형식 문제
            throw new BadRequestException(Utils.getMessage("INVALID_FORMAT.JWT_TOKEN", "validation"));
        }
    }
}*/
