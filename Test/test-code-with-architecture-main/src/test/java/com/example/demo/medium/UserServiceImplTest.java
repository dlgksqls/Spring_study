package com.example.demo.medium;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
@Slf4j
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @MockBean
    private JavaMailSender mailSender;

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        // given
        String email = "testemail393939@gmail.com";

        // when
        User result = userServiceImpl.getByEmail(email);

        // then
        assertThat(result.getNickname()).isEqualTo("test");
    }

    @Test
    void getByEmail은_PENDING_상태인_유저는_찾아올_수_없다() {
        // given
        String email = "testemail39393939@gmail.com";

        // when
        // then
        assertThatThrownBy(() -> {
            userServiceImpl.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById는_ACTIVE_상태인_유저를_찾아올_수_있다() {
        // given
        // when
        User result = userServiceImpl.getById(1);

        // then
        assertThat(result.getNickname()).isEqualTo("test");
    }

    @Test
    void getById는_PENDING_상태인_유저는_찾아올_수_없다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            User result = userServiceImpl.getById(2);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userCreateDto_를_이용하여_유저를_생성할_수_있다() {
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("kok202@kakao.com")
                .address("Gyeongi")
                .nickname("kok202-k")
                .build();
        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // when
        User result = userServiceImpl.create(userCreate);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
        // assertThat(result.getCertificationCode()).isEqualTo("T.T"); // FIXME
    }

    @Test
    void userUpdateDto_를_이용하여_유저를_수정할_수_있다() {
        // given
        UserUpdate userUpdate = UserUpdate.builder()
                .address("Incheon")
                .nickname("kok202-n")
                .build();

        // when
        userServiceImpl.update(1, userUpdate);

        // then
        User userEntity = userServiceImpl.getById(1);
        assertThat(userEntity.getId()).isNotNull();
        assertThat(userEntity.getAddress()).isEqualTo("Incheon");
        assertThat(userEntity.getNickname()).isEqualTo("kok202-n");
    }

    @Test
    void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
        // given
        // when
        userServiceImpl.login(1);

        // then
        User userEntity = userServiceImpl.getById(1);
        assertThat(userEntity.getLastLoginAt()).isGreaterThan(0L);
        // assertThat(result.getLastLoginAt()).isEqualTo("T.T"); // FIXME
    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
        // given
        // when
        userServiceImpl.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

        // then
        User userEntity = userServiceImpl.getById(2);
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            userServiceImpl.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }

}