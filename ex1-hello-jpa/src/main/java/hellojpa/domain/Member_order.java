package hellojpa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member_order {

    @Id @GeneratedValue // 생략 하면 auto 가 default
    @Column(name = "MEMBER_ID")
    private Long id;

    private String userName;

    // 주소
//    @Column(length = 10) // 길이가 10 넘으면 안됨
//    private String name;
//    private String street;
//    private String zipcode;
    @Embedded
    private Address homeAddress;

    // 기간 Period
//    LocalDateTime startDate;
//    LocalDateTime endDate;
    @Embedded
    private Period workPeriod;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
