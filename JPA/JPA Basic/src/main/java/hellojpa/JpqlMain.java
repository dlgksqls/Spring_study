package hellojpa;

import hellojpa.domain.Member_order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // m은 객체 자체를 조회해와를 뜻함
//            List<Member_order> result = em.createQuery(
//                    "select m from Member_order m where m.userName like '%kim%'",
//                    Member_order.class
//            ).getResultList();

            // Criteria 사용 준비
            // 안씀 ㅎㅎ
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Member_order> query = cb.createQuery(Member_order.class);
//
//            Root<Member_order> m = query.from(Member_order.class);
//
//            CriteriaQuery<Member_order> cq = query.select(m).where(cb.equal(m.get("userName"), "kim"));
//            List<Member_order> resultList = em.createQuery(cq)
//                            .getResultList();

            // flush -> commit, query 할때 flush 동작함

            // native 쿼리
            String sql = "select member_id from member_order where userName = 'kim'";
            List<Member_order> resultList = em.createNativeQuery(sql).getResultList();


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
