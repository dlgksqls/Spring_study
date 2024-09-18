package hellojpa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class Member_order {

    @Id @GeneratedValue // 생략 하면 auto 가 default
    @Column(name = "MEMBER_ID")
    private Long id;

    private String userName;

    /**
     * 값 타입 컬렉션
     * 모든 생명주기가 member에 의존함
     */
    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
    @JoinColumn(name = "MEMGER_ID")
    )
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "ADDRESS", joinColumns =
//    @JoinColumn(name = "MEMBER_ID")
//    )
//    private List<Address> addressHistory = new ArrayList<>();
    /**
     * 값타입 보다는 엔티티 하나 더 만들어서 이렇게 써라....
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();

    /**
     * Embedded를 사용하면 더 객체지향적으로 설계 가능
     */
    // 주소
//    @Column(length = 10) // 길이가 10 넘으면 안됨
//    private String name;
//    private String street;
//    private String zipcode;
    @Embedded
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "WORK_ZIPCODE"))
    })
    private Address workAddress;

    // 기간 Period
//    LocalDateTime startDate;
//    LocalDateTime endDate;
    @Embedded
    private Period workPeriod;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
