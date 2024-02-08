package Member_chat_service.configs.entities;

import Member_chat_service.configs.commons.contants.MemberType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor  // 기본 생성자 추가
public class Member  extends Base{
    // 베이스 상속으로 날짜 데이터 가져오기
    @Id     // 기본키
    @GeneratedValue // 자동증가
    private Long seq; // 회원번호

    @Column(length = 65, unique = true, nullable = false)
    private String email;   // 이메일

    @Column(length = 65, nullable = false)
    private String password;    // 비밀번호

    @Column(length = 40, nullable = false)
    private String name;    // 이름

    @Column(length = 11)
    private String mobile;  // 모바일

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private MemberType type = MemberType.USER;    // 회원 타입 기본값



}
