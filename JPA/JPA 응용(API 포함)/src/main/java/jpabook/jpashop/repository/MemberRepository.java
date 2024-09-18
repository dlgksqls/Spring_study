package jpabook.jpashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // select m from Member m where name =
    List<Member> findByName(String name);
}
