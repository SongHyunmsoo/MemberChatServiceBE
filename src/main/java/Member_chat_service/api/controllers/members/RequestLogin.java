package Member_chat_service.api.controllers.members;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RequestLogin(
        @NotBlank
        String email,
        @NotBlank
        String password
) {}
