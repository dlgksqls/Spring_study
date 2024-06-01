package hellojpa.memberTeam;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Column(name = "INSERT_MEMBER")
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private String lastModifiedDate;
}
