package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    /**
     * JPA Criteria
     * 안씀 유지보수성 0에 수렴
     */
    public List<Order> findAllByCriteria(OrderSearch orderSearch){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null){
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        // 회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())){
            Predicate name =
                    cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }

    public List<Order> findAllByString(OrderSearch orderSearch) {
        //language=JPAQL
        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }
        TypedQuery<Order> query = em.createQuery(jpql, Order.class) .setMaxResults(1000); //최대 1000건
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }


//    public List<Order> findAll(OrderSearch orderSearch){
//    }
    public List<Order> findAllWithMemberDelivery() { // 이런식으로 엔티티를 조회하는 식으로 사용해야함, xToOne관계는 fetch join
        return em.createQuery(
                "select o from Order  o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).getResultList();
    }

    public List<Order> findAllWithMemberDelivery(int offset, int limit) { // 이런식으로 엔티티를 조회하는 식으로 사용해야함, xToOne관계는 fetch join
        return em.createQuery(
                "select o from Order  o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d" , Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * 레포지토리는 엔티티를 조회할때 사용해야함,, 하지만 이 방법은 dto를 조회함,,,
     * 이렇게 dto를 뽑아야 할 때에는 repository 안에 query 패키지로 따로 만들기,,,
     * ex) repository.order.simpleQuery.OrderSimpleQueryDto
     */
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                "select o from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }

    /**
     * 오더 2개, 오더 아이템 4개 일 경우, 그리고 각 오더마다 아이템이 두개 인 경우
     * order orderItem
     *   1       1
     *   1       2
     *   2       3
     *   2       4
     * 이렇게 조회되서 문제임,,,
     * 결과적으로 오더는 2개인데, 아이템이 4개라서 4개가 조회됨
     *
     * fetch join paging 문제 해결
     * xToOne 관계는 무조건 fetch join 하셈
     * 컬렉션은 지연로딩으로 조회
     * hibernate.default_batch_fetch_size: 글로벌 설정
     * @BatchSize: 개별 최적화
     */
    public List<Order> findAllWithItem() {
        return em.createQuery(
                "select o from Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d" +
                " join fetch o.orderItems oi" +
                " join fetch oi.item i", Order.class)
                .getResultList();

        // distinct를 사용하면 중복되는 칼럼 제거해줌 하지만 최신 버전은 패치조인에서 distinct를 적지 않아도 자동 제공
//        return em.createQuery(
//                "select distinct o from Order o" +
//                        " join fetch o.member m" +
//                        " join  fetch o.delivery d" +
//                        " join  fetch  o.orderItems oi" +
//                        " join  fetch oi.item i", Order.class)
//                .setFirstResult(0) // fetch join을 한 상태에서 페이징을 사용하면 메모리에서 페이징하기 때문에 상당히 위험함
//                .setMaxResults(100)
//                .getResultList();

    }
}
