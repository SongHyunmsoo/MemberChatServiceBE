package Member_chat_service.member.service;

import Member_chat_service.member.entities.Member;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
/**
 * UserDetails 스프링 시퀴리티가 제공하는 메서드
 */
@Data
@Builder
public class MemberInfo implements UserDetails {

    private String email;
    private String password;
    private Member member; // 그외의 정보
    private boolean enable; // 탈퇴여부
    private boolean lock; // 잠금
    private Collection<? extends GrantedAuthority> authorities; // 권한

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
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !lock;
    } // 잠겨있는지

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    } // 탈퇴여부
}
