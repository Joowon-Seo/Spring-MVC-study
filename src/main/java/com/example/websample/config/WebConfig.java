package com.example.websample.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean loggingFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1); // 필터가 다중으로 있을 때 순서대로 필터를 처리해야 할 때 사용
        filterRegistrationBean.addUrlPatterns("/*"); // 특정 url에 필터를 거는 부분

        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) { // registry 등록한 내역을 관리하는 등록 책
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/*", "/images/*"); // 이 부분은 인터셉터를 탈 일이 없음
    }
}
