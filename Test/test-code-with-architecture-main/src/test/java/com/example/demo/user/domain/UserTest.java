package com.example.demo.user.domain;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserTest {

    @Test
    public void User는_UserCreate_객체로_생성할_수_있다(){
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("testemail393939@gmail.com")
                .nickname("test")
                .address("test")
                .build();

        // when
        User user = User.from(userCreate, new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));

        // then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("testemail393939@gmail.com");
        assertThat(user.getNickname()).isEqualTo("test");
        assertThat(user.getAddress()).isEqualTo("test");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    }

    @Test
    public void User는_UserUpdate_객체로_데이터를_업데이트_할_수_있다(){
        // given
        User user = User.builder()
                .id(1L)
                .email("testemail393939@gmail.com")
                .nickname("test")
                .address("test")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("re-test")
                .address("re-test")
                .build();

        user = user.update(userUpdate);

        // when
        // then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("testemail393939@gmail.com");
        assertThat(user.getNickname()).isEqualTo("re-test");
        assertThat(user.getAddress()).isEqualTo("re-test");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(user.getLastLoginAt()).isEqualTo(100L);
    }

    @Test
    public void User는_로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다(){
        // given
        User user = User.builder()
                .id(1L)
                .email("testemail393939@gmail.com")
                .nickname("test")
                .address("test")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        // when
        user = user.login(new TestClockHolder(1678530673L));

        // then
        assertThat(user.getLastLoginAt()).isEqualTo(1678530673L);
    }

    @Test
    public void User는_유효한_인증_코드로_계정을_활성화_할_수_있다(){
        // given
        User user = User.builder()
                .id(1L)
                .email("testemail393939@gmail.com")
                .nickname("test")
                .address("test")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        // when
        user = user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        // then
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    public void User는_잘못된_인증_코드로_계정을_활성화_하려하면_에러를_던진다(){
        // given
        User user = User.builder()
                .id(1L)
                .email("testemail393939@gmail.com")
                .nickname("test")
                .address("test")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        // when
        // then
        assertThatThrownBy(()->user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}
