package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){

        // 변경 감지 transactional 사용
        // 자동 save 하지 않아도 update 해줌
        // 변경 감지를 사용해라
        Item item = itemRepository.findOne(itemId);
//        item.change(name, price, stockQuantity); // set은 비즈니스 로직에서 되도록 쓰지 마라. 이런식으로 change를 만들어라
        item.setPrice(price); // set을 이렇게 써놓으면 추적하기 너무 어려워짐
        item.setName(name);
        item.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
