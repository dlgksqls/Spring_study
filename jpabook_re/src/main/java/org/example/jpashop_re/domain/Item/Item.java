package org.example.jpashop_re.domain.Item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.jpashop_re.domain.Category;

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

}
