package org.example.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpqlMain5 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member" + 10);
            member.setAge(10);
            member.setType(MemberType.admin);
            em.persist(member);

            Member member1 = new Member();
            member1.setUsername(null);
            member1.setAge(10);
            member1.setType(MemberType.admin);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자");
            member2.setAge(10);
            member2.setType(MemberType.admin);
            em.persist(member2);

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            member.setTeam(team);
            member.changeTeam(team);

            em.flush();
            em.clear();

            String query =
                    "select " +
                            "case when m.age <= 10 then '학생요금' " +
                            "     when m.age >= 60 then '경로요금' " +
                            "     else '일반 요금' " +
                            "end " +
                            "from Member m";
            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            for(String m : result){
                System.out.println("m = " + m);
            }

            System.out.println("=========================================");

            String query1 = "select coalesce(m.username, '이름 없는 회원') as username from Member m";
            List<String> result1 = em.createQuery(query1, String.class)
                    .getResultList();

            for(String m : result1){
                System.out.println("m = " + m);
            }

            System.out.println("=========================================");

            // 특정 이름 숨길때 사용
            String query2 = "select nullif(m.username, '관리자') as username from Member m";
            List<String> result2 = em.createQuery(query2, String.class)
                    .getResultList();

            for(String m : result2){
                System.out.println("m = " + m);
            }

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
