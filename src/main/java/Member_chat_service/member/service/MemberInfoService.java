package Member_chat_service.member.service;

import Member_chat_service.member.constants.Authority;
import Member_chat_service.member.entities.Member;
import Member_chat_service.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;
    // 가져오기 위한 코드

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        // 이메일로 로그인 하기 위해서
        // orElseThrow 예외 발생시 던진다
        Authority authority = Objects.requireNonNullElse(member.getAuthority(), Authority.USER);
        // 널일때 유저를 가져온다
        List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority(authority.name())
        );

        return MemberInfo.builder() // builder 참조주소 동일
                .email(member.getEmail())   // 이메일
                .password(member.getPassword()) // 비밀번호
                .enable(member.isEnable())  // 탈퇴여부
                .lock(member.isLock())  // 잠금확인
                .authorities(authorities)
                .member(member)
                .build();
    }
}
