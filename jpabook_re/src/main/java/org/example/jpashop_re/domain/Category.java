package org.example.jpashop_re.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.jpashop_re.domain.Item.Item;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany // 다대 다는 중간 테이블이 있어야함
    @JoinTable(name = "category_item", // 중간 테이블 이름
            joinColumns = @JoinColumn(name = "category_id"), // 내꺼 칼럼
            inverseJoinColumns = @JoinColumn(name = "item_id")) // 반대편 꺼 칼럼
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
