package study.datajpa.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    // 도메인 클래스 컨버터는 간단한 경우에 조회용으로만 사용해야함
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member){
        return member.getUsername();
    }

    // 127.0.0.1:8080/members?page=3&size=3&sort=id,desc
    // 아이디 순으로 내림차순
    // 페이지 당 3개씩 데이터를 빼냄
    // default page = 0, size = 20, asc
    @GetMapping("/members")
    public Page<Member> list(Pageable pageable){
        return memberRepository.findAll(pageable);
    }

    @GetMapping("/members2")
    public Page<Member> list2(@PageableDefault(size = 5) Pageable pageable){
        return memberRepository.findAll(pageable);
    }

    @GetMapping("/members3")
    public Page<MemberDto> list3(Pageable pageable){
        Page<Member> page= memberRepository.findAll(pageable);
        Page<MemberDto> map = page.map(member -> new MemberDto(member));
        // Page<MemberDto> map = page.map(MemberDto::new;
        return map;
    }


    @PostConstruct
    public void init(){
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" + i, i));
        }
    }
}
