package org.example.jpashop_re.service;

import jakarta.persistence.EntityManager;
import org.example.jpashop_re.domain.Address;
import org.example.jpashop_re.domain.Item.Book;
import org.example.jpashop_re.domain.Item.Item;
import org.example.jpashop_re.domain.Member;
import org.example.jpashop_re.domain.Order;
import org.example.jpashop_re.domain.OrderStatus;
import org.example.jpashop_re.exception.NotEnoughStockException;
import org.example.jpashop_re.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        // given
        Member member = createMember();

        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        Assertions.assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류의 수가 정확해야 한다.");
        Assertions.assertEquals(1000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
        Assertions.assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }

    //@Test(expected = NotEnoughStockException)
    @Test
    public void 상품주문_재고수량초과() throws Exception{
        // given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;

        // when
        NotEnoughStockException exception = assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        });

        // 추가적으로 예외 메시지를 검증하고 싶다면
        assertEquals("재고 수량이 부족합니다.", exception.getMessage());
    }

    @Test
    public void 주문취소() throws Exception{
        // given

        // when

        // then
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원 1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

}