package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j // == private final Logger log = LoggerFactory.getLogger(getClass());
@RestController // RestController는 String이 그대로 반환됨, Controller는 view가 반환됨
public class LogTestController {
    // private final Logger log = LoggerFactory.getLogger(getClass()); // 현재 클래스 지정

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";

        // sout은 무조건 다 출력하기에 쓰지 마라
        System.out.println("name = " + name);

        log.trace("trace log = {}", name);

        /**
         * 로그 상태 결정 가능
         */
        log.debug("debug log = {}", name); // 디버그 할때 보는 로그다
        log.info("info log = {}", name); // 중요한 정보다
        log.warn("warn log = {}", name); // 경고다
        log.error("error log = {}", name); // 에러다

        log.info("info log = {}", name);

        return "ok";
    }
}
