package hellojpa.memberTeam;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team") // member의 team으로 mapping이 되어 있다. 읽기만 가능... (조회) 여기에는 값 넣는거 아님
    private List<Member_team> members = new ArrayList<>();

}
