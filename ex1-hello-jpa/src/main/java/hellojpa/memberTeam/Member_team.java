package hellojpa.memberTeam;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 롬복에서 왼만하면 toString 하지마라
// 컨트롤러에서 엔티티를 절대 뽑지마라
@Entity
@Getter @Setter
public class Member_team extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    // 1 대 다 연관관계일때는 안 씀 이것들
    /**
     *  @ManyToOne(fetch = FetchType.EAGER)
     *  이것은 멤버와 team을 같이 사용할때...
     *  쿼리 하나 날릴때 전부 다 값을 가져옴
     *  em.find 할때 조인해서 다 가져온다는 뜻
     */
    /**
     *   다 쪽이 member
     *   lazy로 하면, team을 프록시 객체로 가져옴, 멤버만 조회하도록
     *   프록시를 가져와서 무언가를 터치하 때 초기화 (쿼리 날림)
     *   이건 쿼리 두번 나감
     *   실무에서는 즉시 로딩을 사용 안함 (가급적 지연 로딩만 사용)
     *   실무에서는 그냥 지연 로딩으로 다 발라라
      */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @OneToMany(mappedBy = "memberTeam")
    private List<MemberProduct> members = new ArrayList<>();

    public void changeTeam(Team team){ // 연관관계 편의 매서드.. 양 쪽 값을 다 넣어주자 (이런식으로 하는게 좋음)
        this.team = team;
        team.getMembers().add(this);
    }
}
