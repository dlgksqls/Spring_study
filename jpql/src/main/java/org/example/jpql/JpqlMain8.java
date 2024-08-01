package org.example.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpqlMain8 {
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

            String query = "select m From Member m"; // m.username : 상태 필드 => 경로 탐색의 끝, 더 이상 탐색 x

            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            for(Member member : result){
                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
                // 회원 1, 팀A(SQL)
                // 회원 2, 팀A(1차 캐시) : 영속성 컨택스트
                // 회원 3, 팀B(SQL) // 쿼리 총 3번 발생함

                // 회원 100명 -> N + 1 : 첫번째 쿼리로 얻은 결과로 쿼리를 N번 날리는 것
                // (1 : select m from member m), (N : n개의 데이터를 반복문으로 조회하는 것)
            }

            String query2 = "select m From Member m join fetch m.team";
            // String query2 = "select m From Member m join fetch m.team as m where m.name";
            // 이런식으로 별칭 주지 마라
            // 처음부터 영속성 컨텍스트에 값을 다 넣어놓음
            // 패치 조인은 객체를 그대로 사용할 때 사용해야함

            List<Member> result2 = em.createQuery(query2, Member.class)
                            .getResultList();

            for (Member member : result2){
                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
                // team은 프록시가 아니라 실제 값을 가져옴
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
