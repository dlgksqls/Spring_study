package com.example.restapi;

import com.example.restapi.model.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestApiApplicationTests {

	@Autowired
	private ObjectMapper objectMapper; // objectMapper 은 get메서드의 이름을 따라간다...
	// ex) getName으로 하면 name으로 쏴주고, getUserName으로 하면 UserName이라고 한다.

	@Test
	void contextLoads() throws JsonProcessingException {
		var user = new UserRequest();
		user.setUserName("홍길동");
		user.setUserAge(10);
		user.setEmail("hong@gmail.com");
		user.setIsKorean(true);

		var json = objectMapper.writeValueAsString(user); // 직렬화
		System.out.println(json);

		//var dto = objectMapper.readValue(json, UserRequest.class); // 역직렬화
		//System.out.println(dto);
	}

}
