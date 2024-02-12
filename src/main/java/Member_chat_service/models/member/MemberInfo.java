package Member_chat_service.models.member;

import Member_chat_service.entities.Member;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * UserDetails 스프링 시퀴리티가 제공하는 메서드
 */
@Builder
@Data
public class MemberInfo implements UserDetails {

    private String email;
    private String password;
    private Member member;   // 그외의 정보
    private Collection<? extends GrantedAuthority> authorities;     // 권한

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {  // 계정이 만료되지 않음
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {   // 계정 장금 확인
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {  // 자격증명 (기한 만료,비밀번호 만료)여부 확인
        return true;
    }

    @Override
    public boolean isEnabled() {    // 계정 활성화 여부
        return true;
    }
}
