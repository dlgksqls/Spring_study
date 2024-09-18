package org.example.jpashop_re.service;

import lombok.RequiredArgsConstructor;
import org.example.jpashop_re.domain.Delivery;
import org.example.jpashop_re.domain.Item.Item;
import org.example.jpashop_re.domain.Member;
import org.example.jpashop_re.domain.Order;
import org.example.jpashop_re.domain.OrderItem;
import org.example.jpashop_re.repository.ItemRepository;
import org.example.jpashop_re.repository.MemberRepository;
import org.example.jpashop_re.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //Order order = new Order();

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주무 취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        // 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 취소
        order.cancel();
    }

    /**
     * 검색
     */
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }
}
