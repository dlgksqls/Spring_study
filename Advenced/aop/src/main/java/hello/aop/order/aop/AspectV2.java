package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    /**
     * public 으로 하면 다른 곳에서 참조해서 사용 가능
     */
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){} // pointcut signature

    /**
     * 클래스 이름 패턴이 *Service
     */
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService(){}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point signature
        return joinPoint.proceed();
    }
}
