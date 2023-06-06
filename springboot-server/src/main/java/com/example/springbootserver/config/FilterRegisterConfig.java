package com.example.springbootserver.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.springbootserver.core.filter.MyTempFilter;

@Configuration
public class FilterRegisterConfig {
    
    // 서블릿 필터체인에 등록되어서 동작확인함
    // 서블릿 필터체인이 먼저 실행된다고 알고 있음
    // 시큐리티 필터체인이 동작할때는 왜 동작안함 ?
    // 시큐리티 필터체인에 직접 등록하면 동작함 ..
    @Bean
    public FilterRegistrationBean<?> filter() {
        // 필터 추가하는 방법
        FilterRegistrationBean<MyTempFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new MyTempFilter()); // 서블릿 필터 객체 담기
        registration.addUrlPatterns("/*");
        registration.setOrder(1); // 순서
        return registration;
    }
}
