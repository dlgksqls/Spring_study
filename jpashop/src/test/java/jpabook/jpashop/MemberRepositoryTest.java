//package jpabook.jpashop;
//
//import jpabook.jpashop.domain.Member;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
////import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
////@RunWith(SpringRunner.class)
//@SpringBootTest
//class MemberRepositoryTest {
//
//    @Autowired MemberRepository memberRepository;
//
//    @Test
//    @Transactional // test에 있으면 테스트 끝나고 rollback 해버림
//    @Rollback(value = false)
//    public void testMember() throws Exception{
//        // give
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        // when
//        Long saveId = memberRepository.save(member);
//        Member findMember = memberRepository.find(saveId);
//
//        // then
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        Assertions.assertThat(findMember).isEqualTo(member);
//        System.out.println("findMember == member: " + (findMember == member));
//    }
//
//}