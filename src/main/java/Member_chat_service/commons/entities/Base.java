package Member_chat_service.commons.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)  // 이벤트 감지
public abstract class Base {

    @CreatedDate    // 처음 등록할때 추가
    @Column(updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 제이슨 포멧 타입
    private LocalDateTime createdAt; // 등록일자

    @LastModifiedDate   // 수정할때 추가
    @Column(insertable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 제이슨 포멧 타입
    private LocalDateTime modifiedAt; // 수정일자
                        // 삭제시간
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 제이슨 포멧 타입
    private LocalDateTime deletedAt;
}

