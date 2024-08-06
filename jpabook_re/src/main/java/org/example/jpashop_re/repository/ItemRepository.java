package org.example.jpashop_re.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.jpashop_re.domain.Item.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if (item.getId() == null){ // 아이디값이 없다면 저장
            em.persist(item);
        }
        else{ // 있다면 업데이트.. 변경 감지를 사용하면 바꿀 것만 바뀌는데, merge를 사용하면 모든 필드가 파라미터 값으로 다 바뀌므로,,
            // 파라미터 값이 없다면 null 로 바뀜...
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
