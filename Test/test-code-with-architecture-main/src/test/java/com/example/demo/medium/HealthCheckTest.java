<<<<<<<< HEAD:Test/test-code-with-architecture-main/src/test/java/com/example/demo/medium/HealthCheckTest.java
package com.example.demo.medium;
========
package com.example.demo.common.controller;
>>>>>>>> origin/main:Test/test-code-with-architecture-main/src/test/java/com/example/demo/common/controller/HealthCheckTest.java

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class HealthCheckTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 헬스_체크_응답이_200으로_내려온다() throws Exception {
        mockMvc.perform(get("/health_check.html"))
                .andExpect(status().isOk());
    }
}
