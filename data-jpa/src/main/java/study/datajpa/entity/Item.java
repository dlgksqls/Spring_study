package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class) // base 엔티티에 넣어서 해결함 보통
@Getter
public class Item implements Persistable<String> {

    @Id @GeneratedValue
    private String id;

    @CreatedDate // 실무에서는 이런식으로,,,,
    private LocalDateTime createdDate;

    @Override // Persistable 는 불가피하게 @GeneratedValue를 사용할 수 없는 경우 id값이 존재하는지 존재하지 않는지 커스텀 해야함,,,
    public boolean isNew() {
        return createdDate == null; // createdDate가 없으면 새로운 것임
    }
}
