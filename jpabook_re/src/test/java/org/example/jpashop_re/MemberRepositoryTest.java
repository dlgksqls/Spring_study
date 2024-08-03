package org.example.jpashop_re;

import org.assertj.core.api.Assertions;
import org.example.jpashop_re.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional()
    @Rollback(value = false)
    public void testMember() throws Exception{
        // given
        Member member = new Member();
        member.setName("memberA");

        // when
        Long id = memberRepository.save(member);
        Member findMember = memberRepository.find(id);

        // then 검증
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
        Assertions.assertThat(findMember).isEqualTo(member);
    }

}