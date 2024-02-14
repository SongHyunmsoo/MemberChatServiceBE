package Member_chat_service.api.controllers.members;

import Member_chat_service.commons.Utils;
import Member_chat_service.commons.exceptions.BadRequestException;
import Member_chat_service.commons.rests.JSONData;
import Member_chat_service.models.member.MemberLoginService;
import Member_chat_service.models.member.MemberSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberSaveService saveService;
    private final MemberLoginService loginService;

    /**
     * @RequestBody JSONData 제이슨 데이터이기 떄문에 꼭 넣어야 한다.
     *
     */
    @PostMapping
    public ResponseEntity<JSONData> join(@RequestBody @Valid RequestJoin form, Errors errors)  {
        saveService.save(form,errors);

        errorProcess(errors);

        JSONData data = new JSONData();
        data.setStatus(HttpStatus.CREATED);

        return ResponseEntity.status(data.getStatus()).body(data);

    }

    /**
     *
     * @Valid 검증
     * @RequestBody JSONData 제이슨 데이터 사용
     * ResponseEntity 헤더 통제가능 코드
     * @return
     */
    @PostMapping("/token")
    public ResponseEntity<JSONData> token(@RequestBody @Valid RequestLogin form, Errors errors) {
        errorProcess(errors);

        String accessToken = loginService.login(form);

        /**
         * 0. 토큰은 2개 받는다
         * 1.토큰1 응답 body - JSONData 형식으로
         * 2.토큰2 응답 헤더 - Authorization : Barer 토큰
         * 헤더와 바디에 토큰을 둘다 보낸다
         */

        JSONData data = new JSONData(accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer"+accessToken);

        return ResponseEntity.status(data.getStatus()).headers(headers).body(data);

    }

    /**
     * 에러 발생시 에러 메세지 코드
     * errorProcess 로 메서드 정의
     * @param errors
     */
    private void errorProcess(Errors errors) {
        if (errors.hasErrors()) {
            throw new BadRequestException(Utils.getMessages(errors));
        }

    }
}
