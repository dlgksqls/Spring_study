package org.example.jpql;

import jakarta.persistence.*;

import java.util.List;
import java.util.Queue;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            TypedQuery<Member> query1 = em.createQuery("select m from Member m where m.username = 'member1'", Member.class);
//            query1.setParameter("username", "member1");
//            Member singleResult = query1.getSingleResult();
//            System.out.println("Member1 = " + singleResult.getUsername());

            Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();
            System.out.println("findMember = " + findMember.getUsername());

//            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
//            // 반환 타입이 명확하지 않을때에는 Query 사용
//            // m.username : String, m.age : int
//            Query query3 = em.createQuery("select m.username, m.age from Member m");


            List<Member> resultList = query1.getResultList();
            System.out.println();
            for (Member member1 : resultList){
                System.out.println("member = " + member1.getUsername());
            }


            // 값이 무조건 하나여야함
            Member result = query1.getSingleResult();
            System.out.println("result = " + result.getUsername());
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
