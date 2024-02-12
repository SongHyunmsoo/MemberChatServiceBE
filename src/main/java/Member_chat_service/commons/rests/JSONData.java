package Member_chat_service.commons.rests;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class JSONData {
    private boolean success = true;     // 대이터가 성공시 !!
    private HttpStatus status = HttpStatus.OK;
    @NonNull    // 대이터를 바꾸기 위해 데이터를 매개변수로 만든다.
    private Object data;
    private Object message;     // 메세지 복수를 위해 오브잭트로 받는다
}
