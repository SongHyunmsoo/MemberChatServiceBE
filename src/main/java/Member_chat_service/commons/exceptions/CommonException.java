package Member_chat_service.commons.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

public class CommonException extends RuntimeException {// 공통 에러
    private HttpStatus status;
    private Errors errors;

    public CommonException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public CommonException(Errors errors, HttpStatus status) {
        this.status = status;
        /* 커맨드 객체 검증 실패시  메세지로 가공 */
        this.errors = errors;
        // 에러형태로 넘기면 에러형태로 가져온다

    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Errors getErrors() {
        return errors;
    }
}