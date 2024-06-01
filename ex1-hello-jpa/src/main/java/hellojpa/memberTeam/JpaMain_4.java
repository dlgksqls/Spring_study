package hellojpa.memberTeam;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.List;

public class JpaMain_4 {
    public static void main(String[] args) {
        // jpa의 목적은 자바 컬렉션 처럼 사용하기 위함

        // 데이터베이스와의 연결 (하나만 만들어야함)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //code
        tx.begin(); // 트랜잭션 시작

        try {
            Team team_a = new Team();
            team_a.setName("TeamA");
            em.persist(team_a);

            Team team_b = new Team();
            team_b.setName("TeamB");
            em.persist(team_b);

            Member_team member = new Member_team();
            member.setName("member1");
//            member.setTeamId(team.getId());
            member.changeTeam(team_b); // ******
            member.setCreatedBy("Kim");
            member.setCreatedDate(LocalDateTime.now());
            em.persist(member);

            //team_b.addMember(member); changeTeam 을 쓰거나 addMember 이렇게 쓰거나...

//            Team findTeamId = findMember.getTeam();
//            System.out.println("findTeam = " + findTeamId.getName());


            Team findTeam = em.find(Team.class, 2L);
            //findTeam.getMembers().add(member); // 양쪽에 객체 값 세팅 // ******

            em.flush();
            em.clear();

            List<Member_team> members = findTeam.getMembers();
            for (Member_team m : members){
                System.out.println("m = " + m.getName());
            }

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
