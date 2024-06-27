package org.example.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpqlMain3 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member" + 10);
            member.setAge(10);
            em.persist(member);

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            member.setTeam(team);
            member.changeTeam(team);

            em.flush();
            em.clear();

            String query1 = "select m from Member m inner join m.team t where t.name = 'teamA'";
            String query2 = "select m from Member m left join m.team t where t.name = 'teamA'";
            String query3 = "select m from Member m, Team  t where m.username = t.name";
            // desc == 내림차순
            List<Member> result1 = em.createQuery(query1, Member.class)
                    .getResultList();
            List<Member> result2 = em.createQuery(query2, Member.class)
                    .getResultList();
            List<Member> result3 = em.createQuery(query3, Member.class)
                    .getResultList();

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
