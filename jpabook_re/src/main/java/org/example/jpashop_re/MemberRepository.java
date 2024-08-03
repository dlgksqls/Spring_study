package org.example.jpashop_re;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.jpashop_re.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}