package com.example.restapi.controller;

import com.example.restapi.model.BookQueryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

// 어떠한 주소를 받겠다!!
@Slf4j // log를 위해
@RestController
@RequestMapping("/api") // /api로 시작하는 주소는 이쪽 컨트롤러로 받겠다
public class RestApiController {

    @GetMapping(path="/hello")
    public String hello(){
        // http://localhost:8080/api/hello
        var html = "<html><body><h1>안녕하세요</h1></body></html>";

        //return "Hello Spring Boot";
        return html;
    }

    @GetMapping(path="/echo/{message}/age/{age}/is-man/{isMan}") // 주소 대문자 사용 x
    public String echo(
            @PathVariable(name = "message") String msg,
            @PathVariable int age,
            @PathVariable boolean isMan
    ){ // 이렇게 해도 되고, String message로 해서 똑같이 맞춰줘도 됨
        System.out.println("echo message : " + msg);
        System.out.println("echo message : " + age);
        System.out.println("echo message : " + isMan);

        // TODO 대문자로 변환해서 RETURN

        return msg.toUpperCase();
    }

    // localhost:8080/api/book?category=IT&issuedYear=2023&issued-month=01&issued_day=31
    @GetMapping(path="/book")
    public void queryParam(
            @RequestParam String category,
            @RequestParam String issuedYear,
            @RequestParam(name = "issued-month") String issuedmonth,
            @RequestParam(name = "issued_day") String issuedday
    ){
        System.out.println(category);
        System.out.println(issuedYear);
        System.out.println(issuedmonth);
        System.out.println(issuedday);
    }
// localhost:8080/api/book2?category=IT&issuedYear=2023&issuedmonth=01&issuedday=31
    @GetMapping(path="/book2")
    public void queryParamDto(
//            @RequestParam String category,
//            @RequestParam String issuedYear,
//            @RequestParam(name = "issued-month") String issuedmonth,
//            @RequestParam(name = "issued_day") String issuedday
            BookQueryParam bookQueryParam
    ){
        System.out.println(bookQueryParam);
    }

    // TODO Parameter 2가지 받기. int 형태로 받아서 두 수의 덧셈 곱셈
    // TODO String 타입 boolean 타입도 받아보기

    // localhost:8080/api/module?a=2&b=3
    @GetMapping(path="/module")
    public void module(
            @RequestParam int a,
            @RequestParam int b
    ){
        System.out.println("덧셈의 결과는 : " + (a + b));
        System.out.println("곱셈의 결과는 : " + a * b);
    }
    @DeleteMapping(path = {"/user/{userName}/delete","/user/{userName}/del"})
    public void delete(
            @PathVariable String userName
    ){
        log.info("user-name : {}", userName);
    }
}
