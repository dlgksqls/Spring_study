package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    //@Enumerated(EnumType.ORDINAL) // 이렇게 하면 숫자 형태로 저장이 됨 enum이 추가되면 작살남
    @Enumerated(EnumType.STRING) // 무조건 이걸 쓰셈
    private DeliveryStatus status; //READY, COMP
}
