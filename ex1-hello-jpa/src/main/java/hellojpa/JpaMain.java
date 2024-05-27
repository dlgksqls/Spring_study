package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        // 데이터베이스와의 연결 (하나만 만들어야함)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //code
        tx.begin(); // 트랜잭션 시작

        try {
            Member findMember = em.find(Member.class, 1L);

            findMember.setName("changeHelloA");

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
