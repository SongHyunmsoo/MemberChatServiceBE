package Member_chat_service.api.controllers.members;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RequestJoin(

        @NotBlank @Email
        String email,
        @NotBlank @Size(min = 8)
        String password,
        @NotBlank
        String confirmPassword, // 페스워드 확인

        @NotBlank
        String name,    // 이름
        String mobile,  // 모바일

        @AssertTrue
        Boolean agree   // 약관동의
) {}
