package Member_chat_service.api.controllers;

import Member_chat_service.commons.exceptions.CommonException;
import Member_chat_service.commons.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice("member_chat_service.api.controllers")
public class CommonController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONData> errorHandler(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Object message = e.getMessage();

        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();


            if (commonException.getMessages() != null) message = commonException.getMessages();
        } else if (e instanceof BadCredentialsException) {
            status = HttpStatus.UNAUTHORIZED;
            // BadCredentialsException 에러 발생시 401 로 출력
        } else if (e instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
            // AccessDeniedException 에러 발생시 403 로 출력
            
        }
        // BadCredentialsException -> 400이 아닌 500 나올수 있다.
        // AccessDeniedException -> 403이 아닌 500이 나올수 있다.


        JSONData data = new JSONData();
        data.setSuccess(false);
        data.setStatus(status);
        data.setMessage(message);

        e.printStackTrace();

        return ResponseEntity.status(status).body(data);
    }
}