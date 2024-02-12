package Member_chat_service.api.controllers.members;

import Member_chat_service.commons.Utils;
import Member_chat_service.commons.exceptions.BadRequestException;
import Member_chat_service.commons.rests.JSONData;
import Member_chat_service.models.member.MemberSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public ResponseEntity<JSONData> join(@RequestBody @Valid RequestJoin form, Errors errors)  {
        saveService.save(form,errors);

        if (errors.hasErrors()) {
            throw new BadRequestException(Utils.getMessages(errors));

        }

        JSONData data = new JSONData();
        data.setStatus(HttpStatus.CREATED);

        return ResponseEntity.status(data.getStatus()).body(data);

    }
}
