package study.datajpa.repository;

import study.datajpa.entity.Member;

import java.util.List;

// 사용자 정의 매서드를 정의하는,,,
public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
