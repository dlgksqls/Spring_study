package org.example.jpql;

import jakarta.persistence.*;

import java.util.List;

public class JpqlMain6 {
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

            Member member1 = new Member();
            member1.setUsername(null);
            member1.setAge(10);
            member1.setType(MemberType.admin);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자");
            member2.setAge(10);
            member2.setType(MemberType.admin);
            em.persist(member2);

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            member.setTeam(team);
            member.changeTeam(team);

            em.flush();
            em.clear();

            // 문자 이어가기 ab 로....
            String query = "select concat('a', 'b') from Member m";

            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            for(String s : result){
                System.out.println("s = " + s);
            }

            System.out.println("======================================");

            // 문자열 잘라내
            String query1 = "select substring(m.username, 2, 3) from Member m";

            List<String> result1 = em.createQuery(query1, String.class)
                    .getResultList();

            for(String s : result1){
                System.out.println("s = " + s);
            }

            System.out.println("======================================");

            // 앞의 글자가 어디 위치에 위치하는지?
            String query2 = "select locate('de', 'abcdef') from Member m";

            List<Integer> result2 = em.createQuery(query2, Integer.class)
                    .getResultList();

            for(Integer i : result2){
                System.out.println("i = " + i);
            }

            System.out.println("======================================");

            // 컬렉션의 크기를 변환해줌
            String query3 = "select size(t.members) from Team t";

            List<Integer> result3 = em.createQuery(query3, Integer.class)
                    .getResultList();

            for(Integer i : result3){
                System.out.println("i = " + i);
            }

            System.out.println("======================================");

//            // 사용하는걸 추천하지 않음, 컬렉션 위치 구한는대 사용
//            @OrderColumn
//            String query4 = "select index(t.members) from Team t";
//
//            List<Integer> result4 = em.createQuery(query4, Integer.class)
//                    .getResultList();
//
//            for(Integer i : result4){
//                System.out.println("i = " + i);
//            }

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
