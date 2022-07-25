package com.example.websample.exception;

import com.example.websample.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 모든 컨트롤러에 동일한 Exception을 추가해 줌
// 특정 관점에서 발생하는 Exception을 처리한다는 점에서
// AOP와 비슷함, 그리고 따로 처리해준다는 점에서 Advice가 붙음

// 커스텀 Exception을 만드는 이유는 기존 exception을 발생시키는 것보다
// 더 구체적고 정확한 에러 내용과 원인을 파악할 수 있음

// 보통 커스텀 Exception을 만들고 그 안에 error코드를 enum으로 만들어서 원하는
// 에러 코드를 만들어냄

// 정확한 exception이 정의 돼있을 때, 해당 헨들러로 가고
// 없다면 부모 헨들러가 있는지 찾아보고
// 그것 마저 없다면 최후의 보루인 그냥 Exception에 대한 handler로 찾아가게 된다.

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalAccessException.class)// 예외를 처리해 주는 곳
    public ResponseEntity<ErrorResponse> handleIllegalAccessException(
            IllegalAccessException e){
        log.error("IllegalAccessException is occurred.", e);

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .header("newHeader", "some Value")
                .body(new ErrorResponse(ErrorCode.TOO_BIG_ID_ERROR,
                        "IllegalAccessException is occurred."));
    } // json으로 반환함, 직렬화를 하기에 용이하고, json을 해석해주는 라이브러리가 많기
    // 때문에 다른 코드를 쓰는 곳에서도 쉽게 이용가능

    @ExceptionHandler(WebSampleException.class)// 예외를 처리해 주는 곳
    public ResponseEntity<ErrorResponse> handleWebSampleException(
            WebSampleException e){
        log.error("WebSampleException is occurred.", e);

        return ResponseEntity
                .status(HttpStatus.INSUFFICIENT_STORAGE)
                .body(new ErrorResponse(e.getErrorCode(),
                        "WebSampleException is occurred."));
    }

    @ExceptionHandler(Exception.class)// 가장 최후의 보루(무조건 넣어 놔야함)
    // 일관성있는 에러 코드를 내보낼 수 있음
    public ResponseEntity<ErrorResponse> handleException(
            Exception e){
        log.error("WebSampleException is occurred.", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR,
                        "Exception is occurred."));
    }
}
