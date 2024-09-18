package com.example.restapi.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class) // 해당 클래스의 변수는 스네이크케이스를 사용하겠다. ex) snake_class
public class UserRequest {
    private String userName;
    private Integer userAge;
    private String email;
    private Boolean isKorean; // boolean은 Boolean으로 해줘야함 그래야 set isKoran으로 됨
}
