package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
public class ReauestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {}, age = {}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody // api로 보내기... == @RestController
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge
    ){
        log.info("username = {}, age = {}", memberName, memberAge);
        return "ok"; // http 응답 메시지로 보내기
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age
    ){
        log.info("username = {}, age = {}", username, age);
        return "ok"; // http 응답 메시지로 보내기
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age){
        log.info("username = {}, age = {}", username, age);
        return "ok"; // http 응답 메시지로 보내기
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamV5(
            @RequestParam(required = true) String username, // required = false : 없어도 됨
            @RequestParam (required = false) Integer age // required = true(default) : 무조건 이 파라미터는 있어야함
            // int age = null; => 컴파일 오류
            // Integer age = null; => 정상 작동 (Integer는 객체로 만들어짐)
            // required = false일때 아무 값도 넣지 않으면 null이 들어감
    ){
        log.info("username = {}, age = {}", username, age);
        return "ok"; // http 응답 메시지로 보내기
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = false, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age
    ){
        log.info("username = {}, age = {}", username, age);
        return "ok"; // http 응답 메시지로 보내기
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(
            @RequestParam Map<String, Object> paramMap
            ){
        log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));
        return "ok"; // http 응답 메시지로 보내기
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData){
//        HelloData helloData = new HelloData();
//        helloData.setUsername(username);
//        helloData.setAge(age);

        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("helloData = {}", helloData);


        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData){ // @RequestBody를 무조건 붙여 줘야함
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("helloData = {}", helloData);

        return "ok";
    }
}
