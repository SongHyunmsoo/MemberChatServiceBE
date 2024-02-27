package Member_chat_service.member.repositories;


import Member_chat_service.member.entities.Member;
import Member_chat_service.member.entities.QMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
    Optional<Member> findByEmail(String email);// 이메일로 인증하기 위해서

    default boolean exists(String email) { //이메일 중복 체크
        QMember member = QMember.member;

        return exists(member.email.eq(email));
    }
}
