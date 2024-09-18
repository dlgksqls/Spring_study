package org.example.jpashop_re.Controller;

import lombok.RequiredArgsConstructor;
import org.example.jpashop_re.domain.Item.Item;
import org.example.jpashop_re.domain.Member;
import org.example.jpashop_re.repository.OrderSearch;
import org.example.jpashop_re.service.ItemService;
import org.example.jpashop_re.service.MemberService;
import org.example.jpashop_re.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){

        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

//    @GetMapping("/orders")
//    public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model){
//        orderService.fin
//    }
}
