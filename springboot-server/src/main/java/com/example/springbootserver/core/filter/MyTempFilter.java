package com.example.springbootserver.core.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class MyTempFilter implements Filter {
    // 필터 추가는 아래처럼 하면 됨
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("디버그 : MyTempFilter 동작");
        chain.doFilter(request, response);
    }
}
