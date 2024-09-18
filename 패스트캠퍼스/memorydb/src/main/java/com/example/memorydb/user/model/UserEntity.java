package com.example.memorydb.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user") // 테이블 이름
public class UserEntity  {
    @Id() // 키 설정 해줘야함
    @GeneratedValue(strategy = GenerationType.IDENTITY) // db에 위임
    private Integer id;
    private String name;
    private int score;
}
