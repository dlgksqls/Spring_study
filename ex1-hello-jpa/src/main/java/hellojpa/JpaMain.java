package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {
        // jpa의 목적은 자바 컬렉션 처럼 사용하기 위함

        // 데이터베이스와의 연결 (하나만 만들어야함)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //code
        tx.begin(); // 트랜잭션 시작

        try {

            /**
             * 비영속
             */
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloJpa");

            /**
             * 영속
              */
//            System.out.println("===BEFORE===");
//            em.persist(member); // 1차 캐쉬에 저장
//            System.out.println("====AFTER====");
//
//            Member findMember = em.find(Member.class, 101L);
//            System.out.println(findMember.getId());
//            System.out.println(findMember.getName());

//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//
//            em.persist(member1);
//            em.persist(member2);

            Member_study member = em.find(Member_study.class, 150L);
            //member.setName("ZZZZZ"); // 이걸로 변경해라 (이 시점에 데이터가 변경됨)

            // em.persist(member); // 변경할땐 이걸 호출하지마라

            //em.flush(); // 직접 호출

            System.out.println("===============");

            // 이때 db에 쿼리가 날아감
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
