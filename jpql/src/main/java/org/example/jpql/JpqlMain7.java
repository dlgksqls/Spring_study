package org.example.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpqlMain7 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setUsername("관리자1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            em.persist(member2);

            em.flush();
            em.clear();

            String query = "select m.username From Member m"; // m.username : 상태 필드 => 경로 탐색의 끝, 더 이상 탐색 x
            String query1 = "select m.team From Member m"; // m.team : 단일 값 연관 필드 => 묵시적 내부 조인 발생
            /**
             * 왠만하면 묵시적 내부 조인이 발생하게 짜면 안됨
             * 튜닝하기 불편하기 때문,,, 그래서 sql과 jpql을 비슷하게 하는 것이....
             */
            String query2 = "select m.team.name From Member m"; // m.team.name : 상태 필드
            String query3 = "select t.members From Team t"; // 컬렉션이라 값은 안나옴.....
            String query4 = "select t.members.size From Team t"; // 컬렉션은 그대로 쓰거나 크기 재는 용도로....
            String query5 = "select m.username From Team t join t.members m"; // 이렇게 명시적 조인을 해서 별칭을 얻어서 사용하기
            /**
             * 다 무시하고 묵시적 내부 조인 사용하지 마
             */

            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            for(String s : result){
                System.out.println("s = " + s);
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
