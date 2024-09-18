package com.example.restapi.controller;

import com.example.restapi.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController // 응답값이 json 으로 내려가겠다
//@Controller
@RequestMapping("/api/v1")
public class ResponseApiController {
    @GetMapping(path = "")
    //@ResponseBody // @Controller 을 사용할때 json으로 내려줌
    public ResponseEntity<UserRequest> user(){
        var user = new UserRequest();
        user.setUserName("홍길동");
        user.setUserAge(10);
        user.setEmail("hong@hong.com");

        var response = ResponseEntity.status(HttpStatus.OK)
                .header("x-custom", "hi")
                .body(user); // status 커스텀
        return response;
    }
}
