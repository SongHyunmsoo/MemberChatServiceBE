package Member_chat_service.tests;

import Member_chat_service.api.controllers.members.RequestLogin;
import Member_chat_service.commons.contants.MemberType;
import Member_chat_service.commons.rests.JSONData;
import Member_chat_service.entities.Member;
import Member_chat_service.models.member.MemberSaveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Transactional  // 오류 미리 방지 코드
public class MemberLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberSaveService memberSaveService;

    @Autowired
    private PasswordEncoder encoder;

    private Member member;   // 회원가입 데이터


    @BeforeEach
    void init() {
        member = Member.builder()
                .email("user01@test.org")
                .password(encoder.encode("_aA123456"))
                .name("사용자01")
                .mobile("010-0000-0000")
                .type(MemberType.ADMIN)
                .build();
        memberSaveService.save(member);
    }


    @Test
    @DisplayName("로그인시 토큰이 발급 되는지 확인")
    void loginTest() throws Exception {
        RequestLogin form = RequestLogin.builder()
                .email(member.getEmail())
                .password("_aA123456")
                .build();
        ObjectMapper om = new ObjectMapper();
        String params = om.writeValueAsString(form);

        String body = mockMvc.perform(
                        post("/api/v1/member/token")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .content(params)
                ).andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        JSONData data = om.readValue(body, JSONData.class);
        String accessToken = (String)data.getData();


        mockMvc.perform(get("/api/v1/member/admin")
                        .header("Authorization", "Bearer " + accessToken)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("미로그인 상태(토큰이 없는)시 회원 전용 URL 접근 통제 테스트")
    void guestAccessTest() throws Exception {
        mockMvc.perform(get("/api/v1/member/info"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
