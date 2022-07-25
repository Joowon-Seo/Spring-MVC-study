package com.example.websample.controller;

import com.example.websample.dto.ErrorResponse;
import com.example.websample.exception.ErrorCode;
import com.example.websample.exception.WebSampleException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestController // 해당요청을 누가 처리할지 해당 요청에 맞는 담당자를 mapping하고 해당 controller에서 처리할 수 있게 함
public class SampleController {

    @GetMapping("/order/{orderId}")   //외부의 특정 url이 찾아올 수 있게 하고, 그게 맞게 등록된 메소드를 활용할 수 있게 연결 해줌
    public String getOrder(@PathVariable("orderId") String id) throws IllegalAccessException, SQLIntegrityConstraintViolationException {
        log.info("Get some order : " + id);
        if ("500".equals(id)) {
            throw new WebSampleException(
                    ErrorCode.TOO_BIG_ID_ERROR,
                    "500 is too big orderId."
            );
        }

        if ("3".equals(id)) {
            throw new WebSampleException(
                    ErrorCode.TOO_SMALL_ID_ERROR,
                    "3 is too small orderId."
            );
        }

        if ("4".equals(id)) {
            throw new SQLIntegrityConstraintViolationException(
                    "Duplicated insertion was tried."
            );
        }

        return "order:" + id + ", orderAmount:1000";
    }

//    @ResponseStatus(HttpStatus.FORBIDDEN) //HTTP 응답 코드를 설정 할 수 있음
//    @ExceptionHandler(IllegalAccessException.class)// 예외를 처리해 주는 곳
//    public ErrorResponse handleIllegalAccessException(
//            IllegalAccessException e){
//        log.error("IllegalAccessException is occurred.", e);
//
//        return new ErrorResponse("INVALID ACCESS",
//                "IllegalAccessException is occurred.");
//    } // json으로 반환함, 직렬화를 하기에 용이하고, json을 해석해주는 라이브러리가 많기
//     // 때문에 다른 코드를 쓰는 곳에서도 쉽게 이용가능






    @DeleteMapping("/order/{orderId}")
    public String deleteOrder(@PathVariable("orderId") String id) {
        log.info("Get some order : " + id);
        return "Delete order:" + id;
    }

    @GetMapping("/order")   //
    public String getOrderWithRequestParam
            (@RequestParam(value = "orderId", required = false, defaultValue = "defaultId") String id,
             @RequestParam("orderAmount") String amount) {
        log.info("Get order : " + id + ", amount : " + amount);
        return "order:" + id + ", orderAmount: " + amount;
    }

    @PostMapping("/order")   //
    public String createOrder
            (@RequestBody CreateOrderRequest createOrderRequest, // 데이터 양이 많을 때 body로 받음
             @RequestHeader String userAccountId) {
        log.info("Get order : " + createOrderRequest +
                ", amount : " + userAccountId);
        return "order:" + createOrderRequest.getOrderId() +
                ", orderAmount: " + createOrderRequest.getOrderAmount();
    }


    @PutMapping("/order")
    public String createOrder() {
        log.info("Create order");
        return "order created -> orderID:1, orderAmount:1000";
    }

    @Data // java  getter setter가 자동으로 생성
    public static class CreateOrderRequest {
        private String orderId;
        private Integer orderAmount;


    }
}
