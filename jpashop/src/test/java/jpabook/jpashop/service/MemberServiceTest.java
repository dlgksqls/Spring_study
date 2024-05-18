package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest // 스프링 컨테이너 안에서 테스트를 돌리겠다.
@Transactional // 테스트 안에서 rollback 함
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Test
    public void 회원가입() throws Exception{
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long saveId = memberService.join(member);

        // then
        Assertions.assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception{
        // given
        Member member_1 = new Member();
        member_1.setName("kim");

        Member member_2 = new Member();
        member_2.setName("kim");

        // when
        memberService.join(member_1);

        try {
            memberService.join(member_2);
        } catch (IllegalStateException e){
            return;
        }

        // then
        Assertions.fail("예외가 발생해야 한다.");

    }
}