package com.example.websample.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController // 해당요청을 누가 처리할지 해당 요청에 맞는 담당자를 mapping하고 해당 controller에서 처리할 수 있게 함
public class SampleController {

    @GetMapping("/order/{orderId}")   //외부의 특정 url이 찾아올 수 있게 하고, 그게 맞게 등록된 메소드를 활용할 수 있게 연결 해줌
    public String getOrder(@PathVariable("orderId") String id) throws IllegalAccessException {
        log.info("Get some order : " + id);
        if ("500".equals(id)) {
            throw new IllegalAccessException("500 is not valid orderId.");
        }
        return "order:" + id + ", orderAmount:1000";
    }

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
