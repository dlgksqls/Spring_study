package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 조회에서 읽기 전용이니 리소스 최소화 해서 읽어라는 명령, 조회에는 가급적이면 readOnly = true 를 사용해라
// findMembers, findOne 과 같은 조회에...
// @RequiredArgsConstructor 은 final이 붙은 빈만 생성자를 만들어줌
@RequiredArgsConstructor
public class MemberService {
    /**
     * 필드 DI를 사용하지 말고
     */
//    @Autowired
//    private MemberRepository memberRepository;

    /**
     * 생성자 DI를 사용해야 한다.
     */
    private final MemberRepositoryOld memberRepositoryOld;
    private final MemberRepository memberRepository;

//    // 생성자가 하나인 경우 @Autowired 생략 가능
//    public MemberService(MemberRepositoryOld memberRepositoryOld) {
//        this.memberRepositoryOld = memberRepositoryOld;
//    }

    /**
     * 회원 가입
     */
    @Transactional // readOnly = false 가 기본값임 그래서 전체적으로 readOnly = true 로 두고 수정해야 하는 메서드 앞에 false로 두기
    public Long join(Member member){
        validateDuplidateMember(member);
        memberRepositoryOld.save(member);
        return member.getId();
    }

    private void validateDuplidateMember(Member member) { // 회원 유효성 검사
        List<Member> findMembers = memberRepositoryOld.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers(){
        return memberRepositoryOld.findAll();
    }
    public Member findOne(Long memberId){
        return memberRepositoryOld.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepositoryOld.findOne(id);
        // 변경 감지
        member.setName(name);
    }
}
