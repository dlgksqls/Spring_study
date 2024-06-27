package org.example.jpql;

import jakarta.persistence.*;

import java.util.List;

public class JpqlMain1 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            // join 쿼리 나감
//            List<Member> result = em.createQuery("select m.team from Member m", Member.class)
//                    .getResultList();

            // 하지만 이렇게 써라
//            List<Member> result = em.createQuery("select t from Member m join m.team t", Team.class)
//                    .getResultList();

            /**
             * 서로 다른 값을 가져올때 방법 1
             */
            List resultList1 = em.createQuery("select m.username, m.age from Member m")
                    .getResultList();

            Object o = resultList1.get(0);
            Object[] result1 = (Object[]) o;
            System.out.println("result1 = " + result1[0]);
            System.out.println("result1 = " + result1[1]);

            /**
             * 서로 다른 값을 가져올때 방법 2
             */
            List<Object[]> resultList2 = em.createQuery("select m.username, m.age from Member m")
                    .getResultList();

            Object[] result2 = resultList2.get(0);
            System.out.println("result2 = " + result2[0]);
            System.out.println("result2 = " + result2[1]);

            /**
             * 서로 다른 값을 가져올때 방법 3
             */
            List<MemberDTO> result3 = em.createQuery("select new org.example.jpql.MemberDTO(m.username, m.age)from Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = result3.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());
            System.out.println("memberDTO = " + memberDTO.getAge());

            // select 문에 걸러진 entity는 영속성 컨텍스트에서 관리됨
            // 그래서 찾고나서 값을 바꾸면 디비에 반영
            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            Member findMember = result.get(0);
            findMember.setAge(40);

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
