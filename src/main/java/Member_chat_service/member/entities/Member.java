package Member_chat_service.member.entities;


import Member_chat_service.commons.entities.Base;
import Member_chat_service.member.constants.Authority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor @AllArgsConstructor // 기본 생성자 추가
public class Member extends Base {
    // 베이스 상속으로 날짜 데이터 가져오기

    @Id// 기본키
    @GeneratedValue // 자동증가
    private Long seq; // 회원번호

    @Column(length=80, unique = true, nullable = false) // 널값 허용 안함
    private String email; // 이메일

    @JsonIgnore
    @Column(length=65, nullable = false) // 널값 허용 안함
    private String password; // 비밀번호

    @Column(length=40, nullable = false) // 널값 허용 안함
    private String name; // 이름

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false) // 널값 허용 안함
    private Authority authority = Authority.USER; // 회원 권한 설정

    @Column(name="_lock")   // 회원 통제 여부
    private boolean lock;   // 잠김여부
    private boolean enable; // 탈퇴여부
}
