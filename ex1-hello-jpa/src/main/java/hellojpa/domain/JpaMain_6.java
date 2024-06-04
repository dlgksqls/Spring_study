package hellojpa.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain_6 {
    public static void main(String[] args) {
        // jpa의 목적은 자바 컬렉션 처럼 사용하기 위함

        // 데이터베이스와의 연결 (하나만 만들어야함)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //code
        tx.begin(); // 트랜잭션 시작

        try {
            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("김영한");

            Member_order memberOrder = new Member_order();
            memberOrder.setUserName("Hello!");
            memberOrder.setHomeAddress(new Address("daegu", "street", "zipcode"));
            memberOrder.setWorkPeriod(new Period());

            em.persist(memberOrder);
            em.persist(book);

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}