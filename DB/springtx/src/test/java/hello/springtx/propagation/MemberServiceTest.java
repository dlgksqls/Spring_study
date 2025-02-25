package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired LogRepository logRepository;

    /**
     * memberService    @Transactional : OFF
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON
     */
    @Test
    void outerTxOff_success(){

        // given
        String username = "outerTxOff_success";

        // when
        memberService.joinV1(username);

        // then
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService    @Transactional : OFF
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON Exception
     */
    @Test
    void outerTxOff_fail(){

        // given
        String username = "로그예외_outerTxOff_fail";

        // when
        assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        // then : log 데이터는 롤백된다.
        assertTrue(memberRepository.find(username).isPresent()); // 커밋이므로 채워져있어야함
        assertTrue(logRepository.find(username).isEmpty()); // 롤백이므로 비어있어야함
    }

    /**
     * memberService    @Transactional : ON
     * memberRepository @Transactional : OFF
     * logRepository    @Transactional : OFF
     */
    @Test
    void singleTx(){

        // given
        String username = "outerTxOff_success";

        // when
        memberService.joinV1(username);

        // then
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService    @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON
     */
    @Test
    void singleTxOn_success(){

        // given
        String username = "singleTxOn_success";

        // when
        memberService.joinV1(username);

        // then
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService    @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON Exception
     */
    @Test
    void outerTxOn_fail(){

        // given
        String username = "로그예외_outerTxOff_fail";

        // when
        assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        // then : 모든 데이터가 롤백된다.
        assertTrue(memberRepository.find(username).isEmpty()); // 커밋이므로 채워져있어야함
        assertTrue(logRepository.find(username).isEmpty()); // 롤백이므로 비어있어야함
    }

    /**
     * memberService    @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON Exception
     */
    @Test
    void recoverException_fail(){

        // given
        String username = "로그예외_recoverException_fail";

        // when
        assertThatThrownBy(() -> memberService.joinV2(username)) // log 예외 잡음
                .isInstanceOf(UnexpectedRollbackException.class);

        // then : 모든 데이터가 롤백된다.
        assertTrue(memberRepository.find(username).isEmpty()); // 커밋이므로 채워져있어야함
        assertTrue(logRepository.find(username).isEmpty()); // 롤백이므로 비어있어야함
    }

    /**
     * memberService    @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON(REQUIRES_NEW) Exception
     */
    @Test
    void recoverException_success(){

        // given
        String username = "로그예외_recoverException_success";

        // when
        memberService.joinV2(username);

        // then : member 저장, log 롤백
        assertTrue(memberRepository.find(username).isPresent()); // 커밋이므로 채워져있어야함
        assertTrue(logRepository.find(username).isEmpty()); // 롤백이므로 비어있어야함
    }
}