package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callService;

    /**
     * 생성자 주입하면 무한 순환 문제가 생김
     */
//    public CallServiceV1(CallServiceV1 callService) {
//        this.callService = callService;
//    }

    @Autowired
    public void setCallService(CallServiceV1 callService){
        this.callService = callService;
    }

    public void external(){
        log.info("call external");
        callService.internal(); // 외부 메서드 호출
    }

    public void internal(){
        log.info("call internal");
    }
}
