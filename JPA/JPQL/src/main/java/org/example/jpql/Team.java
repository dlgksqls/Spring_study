package org.example.jpql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Team {

    @Id @GeneratedValue
    private Long id;
    private String name;

    // persistence.xml 파일에 디폴트값 줄 수 있음
    //@BatchSize(size = 100) // 패치 조인으로는 몇개만 가져오기를 사용하면 안되기 때문에 일반 조인으로 100개를 한번에 가져옴
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
}
