package com.example.springbootserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.springbootserver.core.auth.jwt.MyJwtAuthorizationFilter;
import com.example.springbootserver.core.exception.Exception401;
import com.example.springbootserver.core.exception.Exception403;
import com.example.springbootserver.core.util.MyFilterResponseUtil;

@Slf4j
@Configuration
public class WebSecurityConfig {

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // 인증 처리 인터페이스 // JWT 필터가 BasicAuthenticationFilter를 상속할 경우 필요
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean // 커스텀 시큐리티 필터 빈 등록
    MyJwtAuthorizationFilter jwtAuthorizationFilter() {
        return new MyJwtAuthorizationFilter();
    };

    // WebSecurityConfigurerAdapter 는 Spring Security 5에서 deprecated 되었고
    // Spring Security 5 에서는 SecurityFilterChain 을 직접 빈으로 등록하는 방식을 권장함 ( 컴포넌트 선언 )
    // configure 메소드를 오버라이딩하는 방식과 유사하지만 HttpSecurity를 반환해야함

    // HttpSecurity는 SecurityFilterChainProxy에 의해 사용되는 필터체인을 생성한다.
    // SecurityFilterChain은 스프링 시큐리티 디펜던시에 의해서 FilterChainProxy를 통해 빈으로 등록된다.
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. CSRF 해제
        http.csrf().disable(); // postman 접근해야 함!! - CSR 할때!!

        // 2. iframe 거부
        http.headers().frameOptions().disable();

        // 3. cors 재설정
        // http.cors() 는 WebMvcConfig Cors 설정 그대로 적용
        http.cors();
        // http.cors().configurationSource(configurationSource());

        // 4. jSessionId 사용 거부
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 5. form 로긴 해제 (UsernamePasswordAuthenticationFilter 비활성화)
        http.formLogin().disable();

        // 6. 로그인 인증창이 뜨지 않게 비활성화
        http.httpBasic().disable();

        // 7. 커스텀 보안 구성 적용
        // http.apply(new CustomSecurityFilterManager());

        // 8. 인증 실패 처리
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            log.warn("인증되지 않은 사용자가 자원에 접근하려 합니다 : " + authException.getMessage());
            MyFilterResponseUtil.unAuthorized(response, new Exception401("인증되지 않았습니다"));
        });

        // 10. 권한 실패 처리
        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            log.warn("권한이 없는 사용자가 자원에 접근하려 합니다 : " + accessDeniedException.getMessage());
            MyFilterResponseUtil.forbidden(response, new Exception403("권한이 없습니다"));
        });

        // 11. 인증, 권한 필터 설정
        http.authorizeRequests(authorize -> authorize
                // .antMatchers("/","/auth/**").permitAll()
                .antMatchers("/2222").authenticated()
                .antMatchers("/manager/**").access("hasRole('ADMIN') or hasRole('MANAGER')")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll() // 나머지 주소 허용
        );

        // 필터 순서는 Cors - jwt - UsernamePasswordAuthentication - ..
        http.addFilterAfter(
            // new MyJwtAuthorizationFilter(authenticationManager),
            jwtAuthorizationFilter(),
            CorsFilter.class); // 카탈리나 주의 !!

        return http.build();
    }

    // configure 를 오버라이딩하는 방식
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        // private final MyJwtAuthorizationFilter jwtAuthorizationFilter;
        // public CustomSecurityFilterManager(MyJwtAuthorizationFilter jwtAuthorizationFilter) {
        // this.jwtAuthorizationFilter = jwtAuthorizationFilter; }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            super.configure(http); // WebSecurityConfigurerAdapter 의 기본 보안구성 호출
            // AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            // Cors필터 다음 Jwt필터가 실행됨
            http.addFilterAfter(
                    // new MyJwtAuthorizationFilter(authenticationManager),
                    jwtAuthorizationFilter(),
                    CorsFilter.class);
        }
    }

    // CORS
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
        // configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용
        configuration.addAllowedOriginPattern("http://localhost:3000"); // 프론트 앤드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트 였다. 지금은 아닙니다.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}