package com.example.restapi.controller;

import com.example.restapi.model.BookQueryParam;
import com.example.restapi.model.BookRequest;
import com.example.restapi.model.UserRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostApiController {

    @PostMapping("/post/book")
    public BookRequest post(
            @RequestBody
            BookRequest bookRequest // http body로 들어오는 것을 객체로 매핑 @Requestbody 하면 postman에서 안됨
                                    // json 형식을 딱 맞춰서 보내야함 (post man 사용 시 raw => json으로..)
    ){
        System.out.println(bookRequest);
        return bookRequest;
    }

    @PostMapping("/post/people")
    public UserRequest User(
            @RequestBody
            UserRequest userRequest
    ){
        System.out.println(userRequest);
        return userRequest;
    }
}
