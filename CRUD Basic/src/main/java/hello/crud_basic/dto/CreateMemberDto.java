package hello.crud_basic.dto;

import lombok.Getter;

@Getter
public class CreateMemberDto {

    private String name;

    private String password;

    private String sex;
}
