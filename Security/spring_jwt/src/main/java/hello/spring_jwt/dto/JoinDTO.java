package hello.spring_jwt.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JoinDTO {

    private String username;
    private String password;
}
