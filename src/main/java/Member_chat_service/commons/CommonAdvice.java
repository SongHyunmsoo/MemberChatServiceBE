package Member_chat_service.commons;

import Member_chat_service.commons.exceptions.CommonException;
import Member_chat_service.commons.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice("Member_chat_service")
@RequiredArgsConstructor
public class CommonAdvice {

    private final Utils utils;
    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class) // 모든 에러를 잡는 핸들러
    public ResponseEntity<JSONData> errorHandler(Exception e) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        Object message = e.getMessage();  // 메세지를 가져옴

        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
            Errors errors = commonException.getErrors();
            if (errors != null) {
                Map<String, List<String>> messages = utils.getErrorMessages(errors);
                if (messages != null && !messages.isEmpty()) message = messages;
            }

        } else if (e instanceof AccessDeniedException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (e instanceof BadCredentialsException) { // 아아디와 비번 불일치
            status = HttpStatus.BAD_REQUEST;
            message = messageSource.getMessage("Fail.login.credential", null, null);
        }

        e.printStackTrace();

        JSONData data = new JSONData();
        data.setSuccess(false);
        data.setStatus(status); // 상태값
        data.setMessages(message);  // 메세지값

        return ResponseEntity.status(status).body(data);
    }
}
