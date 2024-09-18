package hellojpa.parentChild;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Parent {

    @Id @GeneratedValue
    private Long id;

    private String name;

    /**
     * cascade = CascadeType.All
     * 하나의 부모가 자식들을 관리할 때 쓰면 좋음
     * 파일을 여러 엔티티에서 관리하면 쓰면 안됨
      */

    /**
     * orphanRemoval = true
     * 부모 객체에서 제거하면 객체가 자동으로 제거됨
     * 참조 하는 곳이 하나일 때 사용해야함
     * 특정 엔티티가 개인 소유할 때 사용
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true) // 부모가 저장될 때 자식도 저장 (영속성 전이)
    private List<Child> childList = new ArrayList<>();

    public void addChild(Child child){
        childList.add(child);
        child.setParent(this);
    }
}
