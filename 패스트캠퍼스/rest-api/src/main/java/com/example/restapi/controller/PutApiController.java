package com.example.restapi.controller;

import com.example.restapi.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class PutApiController {
    @PutMapping("/put")
    public void put(
            @RequestBody
            UserRequest userRequest
    ){
        log.info("Request : {}", userRequest);
    }
}
