package Member_chat_service.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration      // 설정
@EnableJpaAuditing  // 이벤트 감지 활성화
public class MvcConfig implements WebMvcConfigurer {

}
