package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

//    @Autowired
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }
    @GetMapping("/add")
    public String addForm(){
        return"basic/addForm";
    }
    //@PostMapping("/add")
    public String addItemV1(
            @RequestParam String itemName,
            @RequestParam int price,
            @RequestParam int quantity,
            Model model
    ){
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model){

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }
    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {

        // Item -> item
        // HelloData -> hellodata
        // 클래스의 앞 글자만 소문자로 바뀌어서 저장됨

        // model.addAttribute("helloData", item) 이렇게 됨....

        itemRepository.save(item);

        return "basic/item";
    }
    //@PostMapping("/add")
    public String addItemV4(Item item){

        // Item -> item
        // HelloData -> hellodata
        // 클래스의 앞 글자만 소문자로 바뀌어서 저장됨

        // model.addAttribute("helloData", item) 이렇게 됨....

        itemRepository.save(item);

        return "basic/item";
    }

    /**
     * PRG - Post/Redirect/Get
     */
    //@PostMapping("/add")
    public String addItemV5(Item item){

        itemRepository.save(item);

        return "redirect:/basic/items/" + item.getId();
    }
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId()); // 밑에 url에 들어감
        redirectAttributes.addAttribute("status", true); // 들어가지 못한 애들은 쿼리파라미터로 들어감
        // http://localhost:8080/basic/items/3?status=true

        return "redirect:/basic/items/{itemId}";
    }
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }
    @PostMapping("{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    @PostConstruct
    public void init(){
        itemRepository.save(new Item("ItemA", 10000, 10));
        itemRepository.save(new Item("ItemB", 20000, 20));
    }

}
