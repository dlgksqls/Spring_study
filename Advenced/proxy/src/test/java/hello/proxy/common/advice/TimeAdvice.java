package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        // target을 안넣어줘도 됨
        // Object result = method.invoke(target, args);
        Object result = invocation.proceed(); // 알아서 target을 찾아서 실행해줌

        long endTime = System.currentTimeMillis();
        long resultTime = startTime - endTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return result;
    }
}
