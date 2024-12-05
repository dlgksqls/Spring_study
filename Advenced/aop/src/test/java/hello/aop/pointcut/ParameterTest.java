package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(ParameterTest.ParameterAspect.class)
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success(){
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect{

        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember(){}

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1] {}, args={}", joinPoint.getSignature(), arg1);
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2] {}, args={}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg, ..)")
        public void logArgs3(Object arg){
            log.info("[logArgs3] args={}", arg);
        }

        @Before("allMember() && this(obj)") // 프록시 객체 (CGLIB)
        public void thisArgs1(JoinPoint joinPoint, MemberService obj){
            log.info("[this] {}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && target(obj)") // 인터페이스 구현체
        public void thisArgs2(JoinPoint joinPoint, MemberService obj){
            log.info("[target] {}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && @target(annoation)")
        public void thisArgs3(JoinPoint joinPoint, ClassAop annoation){
            log.info("[@target] {}, obj={}", joinPoint.getSignature(), annoation);
        }

        @Before("allMember() && @within(annoation)")
        public void thisArgs4(JoinPoint joinPoint, ClassAop annoation){
            log.info("[@within] {}, obj={}", joinPoint.getSignature(), annoation);
        }

        @Before("allMember() && @annotation(annoation)")
        public void thisArgs3(JoinPoint joinPoint, MethodAop annoation){
            log.info("[@annotation] {}, annotationValue={}, annotationClass={}"
                    , joinPoint.getSignature(), annoation.value(), annoation.getClass());
        }
    }
}
