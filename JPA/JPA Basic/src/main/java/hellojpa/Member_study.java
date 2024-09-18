package hellojpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//@Entity
@Getter @Setter
public class Member_study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // id를 내가 넣으면 안됨.. db가 알아서 함, 문제점 : PK값을 db에 들어가봐야 알 수 있음 그래서 persist를 호출한 후 바로 commit됨
    // 영속성 1차 캐쉬 사용 힘듬 하지만 성능 차이가 그렇게 안남
    private Long id;

    @Column(name = "name", nullable = false) // db컬럼에는 name으로 저장, nullable = false : not null
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING) // Enum은 필수로 string 을 써라,, ordinal은 앞에 추가할 때 마다 순번이 밀린다. 데이터가 문자로 들어감.
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob // varchar 을 넘어가는 큰 데이터
    private String description;
    public Member_study() {
    }
}
