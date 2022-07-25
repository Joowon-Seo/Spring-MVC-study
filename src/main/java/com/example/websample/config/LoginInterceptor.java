package com.example.websample.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    // 인터셉터는 필터와 동일한 기능을 하는 코드를 작성할 수 있다.
    // 다만 더 세밀한 조건식 pre post after를 통한 다양한 저리 가능하다.

    // filter 에서는 특정 요청을 누가 처리하는지 알 수 없었음
    // 하지만 filter를 타고 dispatcher Servlet 이 요청을 누가 처리할지 확인을했고
    // 그 확인 정보를 Handler를 받아왔기 때문에 사용가능
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        log.info("preHandle LogInterceptor " + Thread.currentThread());
        log.info("preHandle LogInterceptor " + handler);

        return true;
    }

    @Override // 해당 controller에서 요청을 처리하고 나올 때 거치는 인터셉터(에러가 없을 시)
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle LogInterceptor " + Thread.currentThread());
    }

    @Override // 항상 무조건 찍고 나오는 try catch에서의 finally과 같은 기능
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        log.info("afterHandle LogInterceptor " + Thread.currentThread());
        if (ex != null){
            log.error("afterCompletion exception : " + ex.getMessage());
        }
    }
}
