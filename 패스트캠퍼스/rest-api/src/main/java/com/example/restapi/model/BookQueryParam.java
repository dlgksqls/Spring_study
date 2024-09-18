package com.example.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data // get, set 자동으로 만들어줌
@AllArgsConstructor // 밑의 변수 네가지를 다 받아야내는 생성자
@NoArgsConstructor // 파라미터를 받지 않은 기본 생성자
public class BookQueryParam {
    private String category;
    private String issuedYear;
    private String issuedMonth;
    private String issuedDay;
}
