package com.example.websample.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
//@Component 여기에 쓰지말고 WebConfig class를 만들어서 사용해줌 왜냐하면 구체적고 다양한 설정을 추가 할 수 있기 때문
public class LogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        log.info("Hello LogFilter : " + Thread.currentThread());
        // 외부 -> filter (-> 처리 ->) filter -> 외부
        chain.doFilter(request, response); // 이부분을 통해서 여러개의 필터가 체인을 통해 다음으로 넘어갈 수 있음
                                           // 필터가 끝나고나면 Spring으로 들어감
        log.info("Bye LogFilter : " + Thread.currentThread() );
    }
}
