package org.example.jpql;

import jakarta.persistence.*;

import java.util.List;

public class JpqlMain9 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select count(m) From Member m"; // m.username : 상태 필드 => 경로 탐색의 끝, 더 이상 탐색 x
            String query2 = "select m from Member m where m = :member";
            String query3 = "select m from Member m where m.id = :memberId";
            String query4 = "select m from Member m  where m.team = :teamId";

            Member findMember = em.createQuery(query2, Member.class)
                    .setParameter("member", member1)
                    .getSingleResult();

            Member findMember2 = em.createQuery(query3, Member.class)
                            .setParameter("memberId", member2.getId())
                                    .getSingleResult();

            List<Member> findMember3 = em.createQuery(query4, Member.class)
                            .setParameter("teamId", teamA)
                                    .getResultList();

            System.out.println("findMember = " + findMember);

            System.out.println("findMember2 = " + findMember2);
            System.out.println();

            for (Member m : findMember3){
                System.out.println("teamA member : " + m);
            }

            TypedQuery<Long> typedQuery = em.createQuery(query, Long.class);


            Long result = typedQuery.getSingleResult();

            System.out.println("result = " + result);


            tx.commit();
        } catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
