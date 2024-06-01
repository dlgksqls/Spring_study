package hellojpa.item_inh;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 상속의 기본 전략은 한 테이블에 다 들어감
/**
 * 단일 테이블 전략은 @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
 * 단일 테이블 전략은 @DiscriminatorColumn(name = "DTYPE") 없어도 자동 생성 해줌
 * 간단할때 사용
  */

/**
 * 구현 클래스 테이블 전략은 @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
 * 조회를 하는데, 부모 클래스 형식으로 조회를 하면 union으로 테이블 다 뒤져봐서 성능 개구려짐
 * 쓰지 마세요^^
 */

/**
 * 처음에 시스템이 단순할때, 객체지향적으로 최대한 생각해보고,,,,
 * 점차 업그레이드 하면서 복잡해질때 테이블 단순화 해보기
 */
@Entity
@Getter @Setter
@DiscriminatorColumn(name = "DTYPE") // default가 DTYPE
@Inheritance(strategy = InheritanceType.JOINED) // 하지만 이렇게 하면 조인 전략을 사용하게됨
public abstract class Item_inh {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;
}
