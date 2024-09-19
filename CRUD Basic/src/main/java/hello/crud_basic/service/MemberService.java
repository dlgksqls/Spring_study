package hello.crud_basic.service;

import hello.crud_basic.dto.CreateMemberDto;
import hello.crud_basic.dto.MemberFindDto;
import hello.crud_basic.entity.Member;
import hello.crud_basic.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findMember(Long id){
        Optional<Member> findMember = memberRepository.findById(id);
        return findMember.orElseThrow(() -> new EntityNotFoundException("not found"));
    }

    public Long save(Member member){
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public Member update(Long id, CreateMemberDto createMemberDto){
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found"));

        findMember.saveMember(createMemberDto);
        Member updateMember = memberRepository.save(findMember);
        return updateMember;
    }

    public void delete(Member member){
        memberRepository.delete(member);
    }
}
