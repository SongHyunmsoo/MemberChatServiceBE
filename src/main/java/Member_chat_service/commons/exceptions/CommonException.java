package Member_chat_service.commons.exceptions;


import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class CommonException extends RuntimeException{// 공통 에러
    private HttpStatus status;

    private Map<String,List<String>> messages;

    public CommonException(Map<String, List<String>> messages,HttpStatus status) {
        super();
        this.status = status;
        this.messages =messages;
    }

    public CommonException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus(){
        return status;
    }
    public Map<String, List<String>> getMessages(){
        return messages;
    }

}
