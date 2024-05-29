package hellojpa;

import hellojpa.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.query.Order;

public class JpaMain_3 {
    public static void main(String[] args) {
        // jpa의 목적은 자바 컬렉션 처럼 사용하기 위함

        // 데이터베이스와의 연결 (하나만 만들어야함)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //code
        tx.begin(); // 트랜잭션 시작

        try {
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
