package hello.exception.edhandler.advice;

import hello.exception.edhandler.ErrorResult;
import hello.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 이 패키지의 컨트롤러에 적용
 * Target all Controllers within specific packages
 * @ControllerAdvice("org.example.controllers")
 * public class ExampleAdvice2 {}
 **/
@Slf4j
@RestControllerAdvice(annotations = RestController.class) // @RestController 가 붙은 컨트롤러만 적용
public class ExControllerAdvice {

    // 예외가 발생하면 얘가 잡음
    // 예외를 잡아서 잘 처리했으므로 200코드가 나감,,,, 이것을 해결하기 위해 @ResponseStatus()를 붙임
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e){
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e){
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    // 처리하지 못하는 오류들은 전부 여기로
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e){
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }
}
