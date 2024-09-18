package org.example.jpashop_re.service;

import lombok.RequiredArgsConstructor;
import org.example.jpashop_re.domain.Item.Book;
import org.example.jpashop_re.domain.Item.Item;
import org.example.jpashop_re.repository.ItemRepository;
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
        Item findItem = itemRepository.findOne(itemId); // 영속성으로 가져와서 고침
        // findItem.change(name, price, stockQuantity); // 이렇게 해야한다고....
        findItem.setName(name); // 변경감지로 인해 영속성 컨텍스트에 있는 것을 자동으로 업데이트 해줌
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);

    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
