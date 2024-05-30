package hellojpa.memberTeam;

import hellojpa.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;

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
            member.setTeam(team_a);
            em.persist(member);

            em.flush();
            em.clear();

            // 연관관계가 없으면 em에게 계속 물어봐야함
            Member_team findMember = em.find(Member_team.class, member.getId());

//            Team findTeamId = findMember.getTeam();
//            System.out.println("findTeam = " + findTeamId.getName());

            Team findTeam = em.find(Team.class, 2L);
            findMember.setTeam(findTeam);
            findMember.getTeam();

            System.out.println("change Team = " + findMember.getTeam().getName());

            List<Member_team> members = findMember.getTeam().getMembers();
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
