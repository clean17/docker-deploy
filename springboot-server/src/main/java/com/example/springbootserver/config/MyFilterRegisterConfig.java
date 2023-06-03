package com.example.springbootserver.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.springbootserver.core.filter.MyTempFilter;

@Configuration
public class MyFilterRegisterConfig {
    @Bean
    public FilterRegistrationBean<?> filter() {
        // 필터 추가하는 방법
        FilterRegistrationBean<MyTempFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new MyTempFilter()); // 서블릿 필터 객체 담기
        // registration.addUrlPatterns("/*");
        registration.addUrlPatterns("http://localhost:3000");
        registration.setOrder(1); // 순서
        return registration;
    }
}
