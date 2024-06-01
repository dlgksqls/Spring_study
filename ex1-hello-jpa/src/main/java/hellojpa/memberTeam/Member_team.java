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
    @ManyToOne() // 다 쪽이 member
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
