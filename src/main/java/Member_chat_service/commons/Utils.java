package Member_chat_service.commons;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor// 빈 선언 의존성 주입을 위해
public class Utils {

    // 에러 코드에 대한 메시지 주입
    private final MessageSource messageSource;


    // Validation에서 발생한 에러 메시지들을 Map 형태로 가져오는 메서드
    public Map<String, List<String>> getErrorMessages(Errors errors) {
        try {
            // getFieldErrors 메서드로 각 필드에 발생한 에러 정보
            // 각 FieldError에서 에러 메세지를 키 값 형태로 가져옴
            Map<String, List<String>> messages = errors.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, e -> _getErrorMessages(e.getCodes()), (m1, m2) -> m2));

            // getGlobalErrors 에러 정보를(ObjectError)를 가져옴
            // ObjectError 에서 에러 코드를 추출 메세지
            // 이 리스트를 "global"이라는 키로 맵에 추가합니다.
            List<String> gMessages = errors.getGlobalErrors()
                    .stream()
                    .map(o -> {
                        try {
                            String message = messageSource.getMessage(o.getCode(), null, null);
                            return message;
                        } catch (Exception e) {
                            return "";
                        }
                    }).filter(s -> !s.isBlank()).toList();

            messages.put("global", gMessages);
            return messages;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    // 주어진 에러 코드를 가져와 리스트로 반환  코드
    private List<String> _getErrorMessages(String[] codes) {
        List<String> messages = Arrays.stream(codes)
                .map(c -> {
                    try {
                        String message = messageSource.getMessage(c, null, null);
                        return message;
                    } catch (Exception e) {
                        return "";
                    }
                })
                .filter(s -> !s.isBlank()).toList();

        return messages;
    }
}
