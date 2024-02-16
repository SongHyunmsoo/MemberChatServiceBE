package Member_chat_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass   // 공통 속성 아이디
@EntityListeners(AuditingEntityListener.class)  // 이벤트 감지
@Getter @Setter

// 상속을 통해 사용하기 위해 추상클래스 선언
public abstract class Base {

    @CreatedDate    // 처음 등록할때 추가
    @Column(updatable = false)
    private LocalDateTime createdAt; // 등록일자

    @LastModifiedDate   // 수정할때 추가
    @Column(insertable = false)
    private LocalDateTime modifiedAt; // 수정일자

}
