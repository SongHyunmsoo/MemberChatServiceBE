package Member_chat_service.member.controllers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestLogin {
    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;
}
