package study.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // 같은 트랜잭션 안에서 영속성 컨텍스트 안에서 동일성을 보장함, 트랜잭션이 다르면 다른 객체임
@Rollback(value = false) // 실무에서는 쓰지 말기
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() throws Exception{
        Member member = new Member("memberA");
        // 커맨드 + 옵션 + v
        Member saveMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(saveMember.getId());

        org.assertj.core.api.Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        org.assertj.core.api.Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        org.assertj.core.api.Assertions.assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() throws Exception{
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();

        // 단건 조회 검증
        org.assertj.core.api.Assertions.assertThat(findMember1).isEqualTo(member1);
        org.assertj.core.api.Assertions.assertThat(findMember2).isEqualTo(member2);

        // 데이터 수정 (더티 체킹)
        member1.setUsername("member!!!!!");

        // 리스트 검증
//        List<Member> all = memberJpaRepository.findAll();
//        org.assertj.core.api.Assertions.assertThat(all.size()).isEqualTo(2);
//
//        // 카운트 검증
//        Long count = memberJpaRepository.count();
//        org.assertj.core.api.Assertions.assertThat(count).isEqualTo(2);
//
//        // 삭제 검증
//        memberJpaRepository.delete(member1);
//        memberJpaRepository.delete(member2);
//
//        long deleteCount = memberJpaRepository.count();
//        org.assertj.core.api.Assertions.assertThat(deleteCount).isEqualTo(0);
    }
}