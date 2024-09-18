package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {

    // return 할인 대상 금액 (얼마나 할인됐는가?)
    int discount(Member member, int price);
}
