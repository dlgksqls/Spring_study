package hellojpa.item_inh;

import hellojpa.memberTeam.Member_team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain_5 {
    public static void main(String[] args) {
        // jpa의 목적은 자바 컬렉션 처럼 사용하기 위함

        // 데이터베이스와의 연결 (하나만 만들어야함)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //code
        tx.begin(); // 트랜잭션 시작

        try {

            Movie_inh movie = new Movie_inh();
            movie.setDirector("aaaa");
            movie.setActor("bbbb");
            movie.setName("바람과 함께 사라지다");
            movie.setPrice(10000);

            Member_team memberTeam = new Member_team();
            memberTeam.setName("Hello");

            em.persist(movie);
            em.persist(memberTeam);

            // 영속성 컨텍스트 (1차 캐시)에 있는 것들을 디비로 날리고
            // 1차 캐시 정리
            em.flush();
            em.clear();

//            Member_team findMember = em.find(Member_team.class, memberTeam.getId());
//            Movie_inh findMovie = em.find(Movie_inh.class, movie.getId());
//
//            System.out.println("findMember = " + findMember.getName());
//            System.out.println("findMovie = " + findMovie.getName());

            // 쿼리 안나감
            Member_team findMember = em.getReference(Member_team.class, memberTeam.getId());
            System.out.println("findMember = " + findMember.getClass()); // 프록시 클래스
            System.out.println("findMember = " + findMember.getId());
            // 쿼리 나감
            System.out.println("findMember = " + findMember.getName());

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
