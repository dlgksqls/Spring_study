package org.example.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpqlMain11 {
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
            // FLUSH : 영속성 컨텍스트에 있는 내용을 디비에 반영
            // CLEAR : 영속성 컨텍스트 비움
            /**
             * 벌크 연산은 영속성 컨텍스트에 반영하지 않고 바로 디비로 수정하기 때문에
             * 위의 회원 1,2,3의 나이는 디비에는 변경되지만 영속성 컨텍스트에는 변경이 안됨
             */
            int resultCount = em.createQuery("update Member m set m.age = 30")
                            .executeUpdate();
            em.clear();

            Member findMember = em.find(Member.class, member1.getId());

            System.out.println(findMember.getAge());

            System.out.println("resultCount = " + resultCount);


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
