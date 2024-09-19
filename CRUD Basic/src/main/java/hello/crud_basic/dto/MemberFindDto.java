package hello.crud_basic.dto;

import lombok.Getter;

@Getter
public class MemberFindDto {

    public MemberFindDto(Long id, String name, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    private Long id;

    private String name;

    private String sex;
}
