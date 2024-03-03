package Member_chat_service.member.controllers;


import Member_chat_service.commons.exceptions.BadRequestException;
import Member_chat_service.commons.rests.JSONData;
import Member_chat_service.member.entities.Member;
import Member_chat_service.member.service.MemberInfo;
import Member_chat_service.member.service.MemberLoginService;
import Member_chat_service.member.service.MemberSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberSaveService saveService;
    private final JoinValidator joinValidator;
    private final MemberLoginService loginService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")  // 로그인시 접근 가능
    public JSONData info(@AuthenticationPrincipal MemberInfo memberInfo) {
        Member member = memberInfo.getMember();

        return new JSONData(member);
    }

    /**
     * @RequestBody JSONData 제이슨 데이터이기 떄문에 꼭 넣어야 한다.
     *
     */
    @PostMapping
    public ResponseEntity join(@Valid @RequestBody RequestJoin form, Errors errors) {

        joinValidator.validate(form, errors);

        errorProcess(errors);

        saveService.join(form);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * @RequestBody JSONData 제이슨 데이터이기 떄문에 꼭 넣어야 한다.
     *
     */
    @PostMapping("/token")
    public JSONData login(@Valid @RequestBody RequestLogin form, Errors errors) {

        errorProcess(errors);

        String token = loginService.login(form);


        return new JSONData(token);
    }


    /**
     * 에러 발생시 에러 메세지 코드
     * errorProcess 로 메서드 정의
     * @param errors
     */
    private void errorProcess(Errors errors) {
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
    }
}
