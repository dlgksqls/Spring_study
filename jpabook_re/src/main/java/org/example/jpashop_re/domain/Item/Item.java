package org.example.jpashop_re.domain.Item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.jpashop_re.domain.Category;
import org.example.jpashop_re.exception.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속하면 부모 클래스에서 상속 전략을 지정해줘야함
// single_table : 하나의 테이블에 자식까지 다 때려박기
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // == 비즈니스 로직 == //
    // 이런 식으로 변경해야함 값을.. setter를 사용할게 아니라

    /**
     * 재고 증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     * 재고 감소
     */
    public void removeStock(int quantity){
        int restStock = stockQuantity - quantity;
        if (restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
