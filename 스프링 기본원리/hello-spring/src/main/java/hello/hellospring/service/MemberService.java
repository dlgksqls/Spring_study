package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    //@Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    public Long join(Member member){
        validateDiplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDiplicateMember(Member member){
        memberRepository.findByName(member.getName()).ifPresent(m -> { // null이 아니면 로직 실행
            throw new IllegalStateException("이미 존재하는 회원입니다.");
            // result.orElseGet() 이런식으로 없으면 어떻게 해라 이렇게 많이 한다고 함
        });
    }

    // 전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
