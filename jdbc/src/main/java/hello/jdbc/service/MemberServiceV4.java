package hello.jdbc.service;


import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 예외 누수 문제 해결
 * SQLException 제거
 *
 * MemberRepository 인터페이스 의존
 * */
@Slf4j
public class MemberServiceV4 {

    //private final DataSource dataSource;
    //private final PlatformTransactionManager transactionManager;
    //private final TransactionTemplate txTemplate;
    //private final MemberRepositoryV3 memberRepository;

    private final MemberRepository memberRepository;

    public MemberServiceV4(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional // 너는 트랜잭션을 처리하는 프록시를 만들어야겠구나!!
    public void accountTransfer(String fromId, String toId, int money){
        bizLogic(fromId, toId, money);
    }
    private void bizLogic(String fromId, String toId, int money){
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    public static void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")){
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}

