package hellojpa.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.lang.reflect.Member;

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
            member1.getFavoriteFoods().add("치킨");
            member1.getFavoriteFoods().add("족발");
            member1.getAddressHistory().add(new AddressEntity("old1", "street", "2"));
            member1.getAddressHistory().add(new AddressEntity("old2", "street", "2"));


            // 임베디드 타입에서 값을 바꾸려면 아예 새로 만들어서 넣어라
            Address newAddress = new Address("seoul", address.getStreet(), address.getZipcode());
            member1.setHomeAddress(newAddress);

            //Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

//            Member_order member2 = new Member_order();
//            member2.setUserName("Hello!!");
//            member2.setHomeAddress(copyAddress); // 복사해서 사용해야함
//            member2.setWorkPeriod(new Period());

            //member2.getHomeAddress().setCity("newCity"); // 사이드이펙트... 임베디드 타입을 여러 객체가 공유하면 값도 같이 바뀜...
            // setter가 없어졌기 때문에 값을 바꾸지 못함. 그래서 아예 사이드이팩트가 일어나지 않게 방지함

            em.persist(member1);
            //em.persist(member2);
            em.persist(book);

            em.flush();
            em.clear();

            System.out.println("======start======"); // 값타입 컬렉션은 지연 로딩임 fetch = lazy로 무조건 해야함
            Member_order findMember = em.find(Member_order.class, member1.getId());

            // daegu => seoul
            //findMember.getHomeAddress().setCity("seoul"); (x)
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("seoul", a.getStreet(), a.getZipcode())); //(o)

            // 치킨 => 한식
            // String 은 통째로 갈아끼워야함..
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            // 객체를 지워야하기 때문에 equals, hashcode 무조건 잘 구현돼있어야함..
            // 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다.
            /**
             * 값 타입 컬렉션 쓰지마라.
             * 값 타입 컬렉션은 값을 바꿀 필요가 없고, 진짜 간단할때 써라
             */
//            findMember.getAddressHistory().remove(new Address("old2", "street", "2"));
//            findMember.getAddressHistory().add(new AddressEntity("newCity1", "street", "2"));

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
