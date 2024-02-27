package Member_chat_service.member.controllers;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
// 회원 가입 요정 클래스
public class RequestJoin {

    @NotBlank @Email
    private String email; // 이메일

    @NotBlank
    @Size(min=8)
    private String password; // 비밀번호

    @NotBlank
    private String confirmPassword; // 비밀번호 확인

    @NotBlank
    private String name; // 유저 이름

    @AssertTrue
    private boolean agree; // 약관동의
}
