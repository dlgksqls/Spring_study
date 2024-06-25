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
            Address address = new Address("daegu", "street", "zipcode");

            Member_order member1 = new Member_order();
            member1.setUserName("Hello!");
            member1.setHomeAddress(address);
            member1.setWorkPeriod(new Period());

            Member_order member2 = new Member_order();
            member2.setUserName("Hello!!");
            member2.setHomeAddress(address);
            member2.setWorkPeriod(new Period());

            // member1.getHomeAddress().setCity("");

            em.persist(member1);
            em.persist(member2);
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
