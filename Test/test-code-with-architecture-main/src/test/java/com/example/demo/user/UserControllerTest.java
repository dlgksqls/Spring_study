package com.example.demo.user;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.mock.TestContainer;
import com.example.demo.user.controller.UserController;
import com.example.demo.user.controller.port.UserReadService;
import com.example.demo.user.controller.port.UserService;
import com.example.demo.user.controller.response.MyProfileResponse;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.service.port.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    @Test
    void 사용자는_특정_유저의_정보를_전달_받을_수_있다() throws Exception {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("testemail393939@gmail.com")
                .nickname("test")
                .address("test")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .lastLoginAt(100L)
                .build());

        // when
        ResponseEntity<UserResponse> result = testContainer.userController.getUserById(1);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("testemail393939@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("test");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다(){
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        // when
        // then
        assertThatThrownBy(() -> {
            testContainer.userController.getUserById(1L);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("testemail393939@gmail.com")
                .nickname("test")
                .address("test")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .lastLoginAt(100L)
                .build());

        // when
        ResponseEntity<Void> result = testContainer
                .userController
                .verifyEmail(1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(302));
        assertThat(testContainer.userRepository.getById(1).getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() throws Exception {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("testemail393939@gmail.com")
                .nickname("test")
                .address("test")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .lastLoginAt(100L)
                .build());

        // when
        // then
        assertThatThrownBy(() -> {
            testContainer.userController.verifyEmail(1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }

    @Test
    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수있다() throws Exception {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(new ClockHolder() {
                    @Override
                    public long millis() {
                        return 1678530673958L;
                    }
                })
                .build();

        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("testemail393939@gmail.com")
                .nickname("test")
                .address("test")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .lastLoginAt(100L)
                .build());

        // when
        ResponseEntity<MyProfileResponse> result = testContainer
                .userController.getMyInfo("testemail393939@gmail.com");

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(1678530673958L);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("testemail393939@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("test");
        assertThat(result.getBody().getAddress()).isEqualTo("test");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_내_정보를_수정할_수_있다() throws Exception {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("testemail393939@gmail.com")
                .nickname("test")
                .address("test")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .lastLoginAt(100L)
                .build());

        // when
        ResponseEntity<MyProfileResponse> result = testContainer
                .userController
                .updateMyInfo("testemail393939@gmail.com", UserUpdate.builder()
                        .address("Paraa")
                        .nickname("sss")
                        .build());

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("testemail393939@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("sss");
        assertThat(result.getBody().getAddress()).isEqualTo("Paraa");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}
