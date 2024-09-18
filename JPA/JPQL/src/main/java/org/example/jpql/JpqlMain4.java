package org.example.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpqlMain4 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member" + 10);
            member.setAge(10);
            member.setType(MemberType.admin);
            em.persist(member);

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            member.setTeam(team);
            member.changeTeam(team);

            em.flush();
            em.clear();

//            다형성 생각했을때 상속관계를 사용하면 이런식으로 사용한다고 함
//            em.createQuery("select i from Item i where type(i) = Book", Item.class);

//            String query1 = "select m.username, 'HELLO', true from Member m " +
//                    "where m.type = org.example.jpql.MemberType.admin";
            String query1 = "select m.username, 'HELLO', true from Member m " +
                    "where m.type = :userType";
            // 보통 이렇게 씀
            List<Object[]> result = em.createQuery(query1)
                    .setParameter("userType", MemberType.admin)
                    .getResultList();

            for (Object[] objects : result){
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);
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
