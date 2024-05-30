package hellojpa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue // 생략 하면 auto 가 default
    @Column(name = "MEMBER_ID")
    private Long id;

    //@Column(length = 10) // 길이가 10 넘으면 안됨
    private String name;
    private String street;
    private String zipcode;
}
