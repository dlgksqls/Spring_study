package com.example.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 전체 파라미터를 가지는 생성자 만들어줌
@NoArgsConstructor // 기본 생성자 만들어줌
public class BookRequest {
    private String name;
    private String number;
    private String category;
}
