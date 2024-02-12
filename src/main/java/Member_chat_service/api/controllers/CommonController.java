package Member_chat_service.api.controllers;

import Member_chat_service.commons.exceptions.CommonException;
import Member_chat_service.commons.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("member_chat_service.api.controllers")
public class CommonController {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<JSONData> errorHandler(Exception e ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Object message = e.getMessage();

        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();

            if (commonException.getMessages() != null) message = commonException.getMessages();
        }

        JSONData data = new JSONData();
        data.setSuccess(false);
        data.setStatus(status);
        data.setMessage(message);

        e.printStackTrace();

        return ResponseEntity.status(status).body(data);
    }
}
