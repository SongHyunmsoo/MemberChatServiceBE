package Member_chat_service.configs.jwt;

import Member_chat_service.commons.Utils;
import Member_chat_service.commons.exceptions.BadRequestException;
import Member_chat_service.models.member.MemberInfo;
import Member_chat_service.models.member.MemberInfoService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;




public class TokenProvider {


    private final String secret; // 민감한 데이터 파이널로 보호
    private final Long tokenValidityInSeconds;   // 민감한 데이터 파이널로 보호

    @Autowired
    private MemberInfoService infoService;

    private Key key;

    public TokenProvider(String secret, Long tokenValidityInSeconds){
        // HMAC -> SHA512 + Message
        this.secret = secret;
        this.tokenValidityInSeconds = tokenValidityInSeconds;

        byte[] bytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(Authentication authentication) { // 토큰 발급 메서드 토큰안에 로그인 정보
        String authories = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); // 권한 관련 코드

        Date expires = new Date(new Date().getTime() + tokenValidityInSeconds * 1000);
            // 현재시간 에서 1시간 까지만 유지

        return Jwts.builder()
                .setSubject(authentication.getName())// 아이디
                .claim("auth",authories)
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
                .parseEncryptedClaims(token)
                // 토큰안에 정보가 저장되어있다.
                .getPayload();
                // 조회코드
        String email = claims.getSubject(); // 이메일 정보를 조회
        MemberInfo userDetails = (MemberInfo)infoService.loadUserByUsername(email);

        String auth = claims.get("auth").toString();
        List<? extends GrantedAuthority> authorities = Arrays.stream((auth.split(",")))
                .map(s -> new SimpleGrantedAuthority(s)).toList();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,token,authorities);

        return authentication;
    }

    public void validateToken(String token) {
        try {
            Claims claims = Jwts.parser() // 토큰 정보를 받아서 분해 해석하는 코드
                    .setSigningKey(key)
                    // 검증 하는역활
                    .build()
                    // 토큰은 감싸주는 역활
                    .parseEncryptedClaims(token)
                    // 토큰안에 정보가 저장되어있다.
                    .getPayload();
                    // 조회코드


        }catch (ExpiredJwtException e) { // 만료된 토큰
            throw new BadRequestException(Utils.getMessage("EXPIRED.JWT_TOKEN","Validation"));

            } catch (UnsupportedJwtException e) { // 지원되지 않는 토큰
                throw  new BadRequestException(Utils.getMessage("UNSUPPORTED.JWT_TOKEN","validation"));
            }catch (SecurityException | MalformedJwtException | IllegalArgumentException e ) {// 형식 문제
        throw new BadRequestException(Utils.getMessage("INVALID_FORMAT.JWT_TOKEN","validation"));

        }
    }
}
