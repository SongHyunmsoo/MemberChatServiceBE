package Member_chat_service.commons;

import Member_chat_service.member.service.MemberInfo;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
// @Component 어노테이션을 사용하여 이 클래스를 스프링 컨테이너에 빈으로 등록
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    // 현재 인증된 사용자의 이메일을 반환하는 메소드
    public Optional<String> getCurrentAuditor() {

        String email = null;
        // 이메일 가져오기
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // SecurityContextHolder를 통해 현재 인증된 사용자의 Authentication 객체를 가져옵니다.

        if (authentication != null && authentication.getPrincipal() instanceof MemberInfo) {
            // Authentication 객체가 null이 아니고, 그 안의 principal 객체가 MemberInfo의 구현체인지 확인합니다.
            MemberInfo memberInfo = (MemberInfo)authentication.getPrincipal();
            // MemberInfo 객체를 가져와서

            email = memberInfo.getEmail();
            // 그 안의 이메일을 email 을 저장합니다.
        }

        return Optional.ofNullable(email);
        // 이메일을 Optional 객체로 감싸서 반환합니다.
        // 이메일 정보가 없는 경우 Optional.empty가 반환됩니다.
    }
}
