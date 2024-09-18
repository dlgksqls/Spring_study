package com.example.memorydb.user.controller;

import com.example.memorydb.user.model.UserEntity;
import com.example.memorydb.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor // 생성자 메서드로 채워달라
public class UserApiController {

    private final UserService userService;
    @PutMapping("")
    public UserEntity create(
            @RequestBody UserEntity userEntity
    ){
        return userService.save(userEntity);
    }

    @GetMapping("/all")
    public List<UserEntity> findAll(){
        return userService.findAll();
    }

    // delete
    @DeleteMapping("/id/{id}")
    public void delete(
            @PathVariable Long id
    ){
        //userService.delete(id);
    }

    // findby id => path variable
    @GetMapping("/id/{id}")
    public UserEntity findOne(
            @PathVariable Long id
    ){
        var response = userService.findById(id);
        return response.get();
    }

    @GetMapping("/score")
    public List<UserEntity> filterScore(
            @RequestParam int score
    ){
        return userService.filterScore(score);
    }

    @GetMapping("/min_max")
    public List<UserEntity> filterScore(
            @RequestParam int min,
            @RequestParam int max
    ){
        return userService.filterScore(min, max);
    }

//    @Query(
//            "select u from user where  u.score >= ?1 AND u.score <= ?2"
//    )
//    public List<UserEntity> score(int min, int max);
}
