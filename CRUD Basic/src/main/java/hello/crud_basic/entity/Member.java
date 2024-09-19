package hello.crud_basic.entity;

import hello.crud_basic.dto.CreateMemberDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String password;

    private String sex;

    public void saveMember(CreateMemberDto createMemberDto){
        this.name = createMemberDto.getName();
        this.password = createMemberDto.getPassword();
        this.sex = createMemberDto.getSex();
    }
}
