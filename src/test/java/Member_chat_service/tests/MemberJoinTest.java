package Member_chat_service.tests;

import Member_chat_service.api.controllers.members.RequestJoin;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class MemberJoinTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("회원 가입 테스트")
    void joinTest () throws Exception {
        RequestJoin form = RequestJoin.builder()
                .email("user01@test.org")
                .password("_aA123456")
                .confirmPassword("_aA123456")
                .name("사용자01")
                .mobile("010-0000-0000")
                .agree(true)
                .build();

        ObjectMapper om = new ObjectMapper();
        String params = om.writeValueAsString(form);
        System.out.println(params);

        mockMvc.perform(post("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .contentType(params)
                .with(csrf().asHeader())

        ).andDo(print());


    }


}