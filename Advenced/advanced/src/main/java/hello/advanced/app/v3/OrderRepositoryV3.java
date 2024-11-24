package hello.advanced.app.v3;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV3 {

    private final LogTrace trace;

    public void save(TraceId traceId, String itemId){

        TraceStatus status = null;

        try {
            status = trace.begin("OrderRepositoryV3.request()");
            // 저장 로직
            if (itemId.equals("ex")){
                throw new IllegalStateException("예외 발생!");
            }
            sleep(10000);
            trace.end(status);
        } catch (Exception e){
            trace.exception(status, e);
            throw e; // 예외를 꼭 다시 던져줘야함(= 그대로 나둬야함 = 정상흐름대로 실행),, 안던지면 예외를 그대로 먹어버림
            // 로그때문에 예외가 없어지면 안됨
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
