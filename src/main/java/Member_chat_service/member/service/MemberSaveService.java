package Member_chat_service.member.service;

import Member_chat_service.member.constants.Authority;
import Member_chat_service.member.controllers.RequestJoin;
import Member_chat_service.member.entities.Member;
import Member_chat_service.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSaveService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public void join(RequestJoin form) {

        // 회원 가입 처리
        String hash = encoder.encode(form.getPassword());

        Member member = Member.builder()
                .email(form.getEmail()) // 이메일
                .name(form.getName())   // 이름
                .password(hash)         // 비밀번호
                .authority(Authority.USER) // 권한
                .lock(false)    // 잠금확인
                .enable(true)   // 탈퇴여부
                .build();

        save(member);
    }

    public void save(Member member) {

        memberRepository.saveAndFlush(member);
        // 데이터 저장
    }

}
