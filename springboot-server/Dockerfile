# 래퍼런스 https://findstar.pe.kr/2022/05/13/gradle-docker-cache/
# Gradle 이미지를 기반으로 Gradle 이미지를 빌드한다.
FROM gradle:7.4-jdk-alpine AS builder
WORKDIR /build

# 도커 캐시 사용 - 새로운 Gradle만 다운로드
COPY build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

# 빌더 이미지에서 애플리케이션 빌드
COPY . /build
# RUN gradle clean build --no-daemon # daemon 은 메모리를 점유하지만 빌드가 빠르다
RUN gradle clean build --parallel 
# -x test 는 테스트 없이 빌드 # RUN gradle build -x test --parallel

# Java 11 JDK가 설치된 Docker 이미지를 기반으로 새 이미지를 만들겠다
FROM openjdk:11.0-slim
WORKDIR /app

# builder이미지에서 빌드한 jar파일을 /app.jar 로복사
COPY --from=builder /build/build/libs/*.jar  /app.jar

# EXPOSE 8080

# ENV MYSQL_DATABASE=${MYSQL_DATABASE}
# ENV MYSQL_USER=${MYSQL_USER}
# ENV MYSQL_PASSWORD=${MYSQL_PASSWORD}
# ENV MYSQL_HOST=${MYSQL_HOST}
# ENV MYSQL_PORT=${MYSQL_PORT}

# Shell 스크립트 복사 - 이 방법으로 환경변수 찾음
# COPY start.sh /start.sh
# RUN sed -i 's/\r$//' /start.sh
# RUN chmod +x /start.sh

#  root 권한 x, nobody 권한으로 실행함
USER nobody
ENTRYPOINT [                                                            \
   "java",                                                              \
   "-Dspring.profiles.active=prod",                                     \
   "-Dfile.encoding=UTF-8",                                             \
   "-Djava.security.egd=file:/dev/./urandom",                           \
   "-Dsun.net.inetaddr.ttl=0",                                          \
   "-jar",                                                              \
   "/app.jar"                                                         \
]

# ENTRYPOINT ["/start.sh"] # 만들 필요가 없었음

# 루트 디렉토리에서 이미지 빌드 ( 덮어씀 ) ->  docker build -t back-test . 		#-t : --tag 이미지 이름, 태그 설정
# docker run -p 8080:8080 back-test
# 동적으로 환경변수 넣어서 테스트 하는 방법  docker run -p 8080:8080 -e HS512_SECRET=metacoding back-test
# 컨테이너 실행 ->   docker run -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mysql://[ec2주소] -e SPRING_DATASOURCE_PASSWORD=[mysql비밀번호] -d back-test
# 컨테이너 로그 확인 ->  docker logs [container ID]
