package com.example.springbootserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 핕터 cors 설정과 별개로 mvc cors 설정
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 3600 * 24;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        // .allowedOriginPatterns("http://localhost:3000","*") // Post Man으로 테스트
        .allowedOriginPatterns("http://localhost:3000") // 프론트에서만 허용 / 빈스톡 주소로 변경
        .allowedMethods("GET","POST","PUT","DELETE","PATCH","OPTIONS")
        .allowedHeaders("*")
        // true 로 설정하면 allowedOrigins을 "*"로 설정할 수 없다 -> OriginPatterns을 사용한다.
        .allowCredentials(true)
        .maxAge(MAX_AGE_SECS);
    }
}
