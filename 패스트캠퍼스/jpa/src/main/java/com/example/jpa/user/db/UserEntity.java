package com.example.jpa.user.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user") // user 테이블과 매칭하겠다.
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id가 어떤식으로 생성될거냐?
    private Integer id;
    private String name;
    private Integer age;
    private String email;
}
