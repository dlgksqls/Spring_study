package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;
    @Autowired EntityManager em;

    @Test
    public void testMember() throws Exception{
        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(saveMember.getId()).get();

        Assertions.assertThat(findMember.getId()).isEqualTo(saveMember.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(saveMember.getUsername());
        Assertions.assertThat(findMember).isEqualTo(saveMember);
    }

    @Test
    public void basicCRUD() throws Exception{
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        // 단건 조회 검증
        org.assertj.core.api.Assertions.assertThat(findMember1).isEqualTo(member1);
        org.assertj.core.api.Assertions.assertThat(findMember2).isEqualTo(member2);

        // 데이터 수정 (더티 체킹)
        // member1.setUsername("member!!!!!");

        // 리스트 검증
        List<Member> all = memberRepository.findAll();
        org.assertj.core.api.Assertions.assertThat(all.size()).isEqualTo(2);

        // 카운트 검증
        Long count = memberRepository.count();
        org.assertj.core.api.Assertions.assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deleteCount = memberRepository.count();
        org.assertj.core.api.Assertions.assertThat(deleteCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() throws Exception{
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 10);

        Assertions.assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        Assertions.assertThat(result.get(0).getAge()).isEqualTo(20);
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testNamedQuery() throws Exception{
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsername("AAA");
        Member findMember = result.get(0);
        Assertions.assertThat(findMember).isEqualTo(member1);
    }

    @Test
    public void testQuery() throws Exception{
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findUser("AAA", 10);
        Member findMember = result.get(0);
        Assertions.assertThat(findMember).isEqualTo(member1);
    }

    @Test
    public void findUsernameList() throws Exception{
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<String> result = memberRepository.findUsernameList();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void findMemberDtoTest() throws Exception{

        Member member1 = new Member("AAA", 10);

        memberRepository.save(member1);

        Team team = new Team("teamA");
        teamRepository.save(team);
        member1.setTeam(team);

        List<MemberDto> result = memberRepository.findMemberDto();

        for (MemberDto memberDto : result) {
            System.out.println("dto = " + memberDto);
        }
    }

    @Test
    public void findByNames() throws Exception{
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findbyNames(Arrays.asList("AAA", "BBB"));

        for (Member s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void returnType() throws Exception{
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> aaa = memberRepository.findListByUsername("AAA");

        // 단건 조회이지만 데이터가 두개 조회되면 예외가 터짐
        Member bbb = memberRepository.findMemberByUsername("AAA");
        Optional<Member> ccc = memberRepository.findOptionalByUsername("AAA");
    }

    @Test
    public void paging() throws Exception{
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;

        // page는 0부터 시작함
        // 0쪽의 3개만큼 가져와라, 나는 username으로 desc로 정렬 할거야!
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // when
        /**
         * api로 바로 반환하면 큰일남
         * dto로 변경해서 변환하기
         */
        Page<Member> page = memberRepository.findPageByAge(age, pageRequest);
        Slice<Member> page_slice = memberRepository.findSliceByAge(age, pageRequest);

        /**
         * 얘를 반환해라
         */

        Page<MemberDto> toMap = page.map(m -> new MemberDto(m.getId(), m.getUsername(), m.getTeam().getName()));


        // 없어도 됨, 반환 타입이 page면 totalcount 쿼리까지 같이 날림
//        long totalCount = memberRepository.totalCount(age);

        // 페이지 계산 공식 적용 ...
        // totalPage = totalCount / size ...
        // 마지막 페이지 ...
        // 최초 페이지 ...

        // then
        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements(); // totalCount

        Assertions.assertThat(content.size()).isEqualTo(3);
        Assertions.assertThat(page.getTotalElements()).isEqualTo(5);
        Assertions.assertThat(page.getNumber()).isEqualTo(0); // page.getNumber : 페이지 번호 가져오기
        Assertions.assertThat(page.getTotalPages()).isEqualTo(2);
        Assertions.assertThat(page.isFirst()).isTrue();
        Assertions.assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void bulkUpdate(){ // 벌크연산은 영속성 컨택스트를 무시하고 디비로 바로 쿼리를 날려버림
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        int resultCount = memberRepository.bulkAgePlus(20);

        List<Member> result = memberRepository.findByUsername("member5");
        Member member5 = result.get(0);
        // 디비에는 쿼리가 나갔지만 영속성 컨택스트에는 바뀌지 않음
        System.out.println("member5 = " + member5);

        // 벌크 연산 후에는 영속성 컨텍스트를 날려줘야함
        em.flush();
        //em.clear();

        List<Member> result_after = memberRepository.findByUsername("member5");
        Member member5_after = result_after.get(0);
        System.out.println("member5 = " + member5_after);

        org.assertj.core.api.Assertions.assertThat(resultCount).isEqualTo(3);
    }

    @Test
    public void findMemberLazy(){
        // given
        // member1 -> teamA
        // member2 -> teamB

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);

        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        // when
        // N + 1 발생
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.teamClass = " + member.getTeam().getClass()); // proxy 객체 가지고 옴
            System.out.println("member.team = " + member.getTeam().getName());
        }

        System.out.println("========================================================================");
        em.flush();
        em.clear();

        List<Member> memberFetch = memberRepository.findMemberFetchJoin();
        for (Member member : memberFetch) {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.teamClass = " + member.getTeam().getClass()); // 진짜 entity가 나옴
            System.out.println("member.team = " + member.getTeam().getName());
        }
    }

    @Test
    public void queryHint(){
        // given
        Member member1 = memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();

        // when
        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");

        em.flush();
    }

    @Test
    public void lock(){
        // given
        Member member1 = memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();

        // when
        Member findMember = memberRepository.findLockByUsername("member1");
        findMember.setUsername("member2");

        em.flush();
    }

    @Test
    public void callCustom() throws Exception{
        List<Member> result = memberRepository.findMemberCustom();
    }
}