package org.example.jpashop_re.service;

import lombok.RequiredArgsConstructor;
import org.example.jpashop_re.domain.Member;
import org.example.jpashop_re.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 트랜잭션 안에서 이루어져야한다, 기본적으로 조회만 가능하게 하고 쓰기가 있어야할때는 join 매서드 처럼 어노테이션 달아주기
//@RequiredArgsConstructor // final 변수만 사용하여 생성자 생성해줌
public class MemberService {

    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    @Transactional // default : readOnly = false
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * id 값으로 회원 조회
     */
    public Member findone(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
