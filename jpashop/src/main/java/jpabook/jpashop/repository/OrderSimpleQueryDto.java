package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private int totalPrice;

    public OrderSimpleQueryDto(Order order){
        this.orderId = order.getId();
        this.name = order.getMember().getName(); // lazy 초기화 : 영속성 컨텍스트가 멤버 아이디를 가지고 쿼리를 날려 찾아옴
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress(); // lazy 초기화
        this.totalPrice = order.getTotalPrice();
    }
}

