package study.datajpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    // 가장 많이 씀.. 컴파일시에 오류를 뱉어주므로 오류 수정 굿
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findbyNames(@Param("names") List<String> names);

    List<Member> findListByUsername(String username); // 컬렉션

    Member findMemberByUsername(String username); // 단건

    Optional<Member> findOptionalByUsername(String username); // 단건 Optional

    // 반환타입 : page, 반환 타입이 page면 totalcount 쿼리까지 같이 날림
    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m")
    Page<Member> findPageByAge(int age, Pageable pageable);

    Slice<Member> findSliceByAge(int age, Pageable pageable); // 카운트 쿼리 안나감

    @Modifying(clearAutomatically = true)
    // 이게 있어야 executeUpdate를 사용함, clearAutomatically = true는 쿼리가 나간 후 깨끗하게 자동으로 해줌
    // em.clear를 안해도 됨
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    /**
     * 아래 세개는 다 같은 것
     */
    @Override
    @EntityGraph(attributePaths = {"team"}) // fetch join
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m ")
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true")) // 난 이것을 읽기전용으로 쓸거야
    // 변경감지 체크 안함
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE) // for update
    Member findLockByUsername(String username);
}
