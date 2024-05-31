package hellojpa.memberTeam;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// 롬복에서 왼만하면 toString 하지마라
// 컨트롤러에서 엔티티를 절대 뽑지마라
@Entity
@Getter @Setter
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team") // member의 team으로 mapping이 되어 있다. 읽기만 가능... (조회) 여기에는 값 넣는거 아님
    //@JoinColumn(name = "TEAM_ID") 1 : 다 연관관계 일때 하지만 잘 안씀
    private List<Member_team> members = new ArrayList<>();

//    public void addMember(Member_team member) {
//        member.setTeam(this);
//        this.members.add(member);
//    }
}
