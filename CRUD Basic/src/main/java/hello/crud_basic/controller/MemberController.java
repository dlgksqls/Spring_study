package hello.crud_basic.controller;

import hello.crud_basic.dto.CreateMemberDto;
import hello.crud_basic.dto.MemberFindDto;
import hello.crud_basic.entity.Member;
import hello.crud_basic.service.MemberService;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequestMapping("/member")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public List<MemberFindDto> viewAllMembers(){
        List<Member> members = memberService.findMembers();
        List<MemberFindDto> dtos = members.stream()
                .map(o -> new MemberFindDto(o.getId(), o.getName(), o.getSex()))
                .collect(Collectors.toList());

        return dtos;
    }

    @GetMapping("/members/{id}")
    public MemberFindDto viewMember(@PathVariable long id){
        Member findMember = memberService.findMember(id);

        MemberFindDto dto = new MemberFindDto(findMember.getId(), findMember.getName(), findMember.getSex());
        return dto;
    }

    @PostMapping("/save")
    public createdMemberResponse save(@RequestBody CreateMemberDto createMemberDto){
        Member member = new Member();
        member.saveMember(createMemberDto);
        Long id = memberService.save(member);
        return new createdMemberResponse(id);
    }

    @PutMapping("/update/{id}")
    public MemberFindDto updateMember(@PathVariable Long id, @RequestBody CreateMemberDto createMemberDto){
        Member updateMember = memberService.update(id, createMemberDto);
        MemberFindDto dto = new MemberFindDto(updateMember.getId(), updateMember.getName(), updateMember.getSex());

        return dto;
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteMember(@PathVariable Long id){
        Member findMember = memberService.findMember(id);
        memberService.delete(findMember);
        return HttpStatus.ACCEPTED;
    }

    @Getter
    static class createdMemberResponse{
        private Long id;

        public createdMemberResponse(Long id) {
            this.id = id;
        }
    }
}
