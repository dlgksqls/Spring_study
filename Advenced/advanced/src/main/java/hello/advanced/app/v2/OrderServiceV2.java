package hello.advanced.app.v2;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;

    public void orderItem(TraceId traceId, String itemId){

        TraceStatus status = null;

        try {
            status = trace.beginSync(traceId, "OrderServiceV2.request()");
            orderRepository.save(status.getTraceId(), itemId);
            trace.end(status);
        } catch (Exception e){
            trace.exception(status, e);
            throw e; // 예외를 꼭 다시 던져줘야함(= 그대로 나둬야함 = 정상흐름대로 실행),, 안던지면 예외를 그대로 먹어버림
            // 로그때문에 예외가 없어지면 안됨
        }
    }
}
