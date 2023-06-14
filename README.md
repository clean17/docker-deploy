# Todo 프로젝트

> ## 개요
 화면을 리액트로 만들고 스프링 서버를 연결시킨 뒤 깃헙액션 + 도커허브 CI/CD 파이프라인을 통해 EB에 배포한다.
 
 임시 배포 링크 : ec2-13-124-79-248.ap-northeast-2.compute.amazonaws.com
<br>

### 화면

![image](https://github.com/clean17/docker-deploy/assets/118657689/b821578d-18d5-453b-b34d-3437855c4c24)

![image](https://github.com/clean17/docker-deploy/assets/118657689/2a7ff880-6e9c-496a-b1e9-b6dc2c43de4e)

### RestDods - /api.html
![image](https://github.com/clean17/docker-deploy/assets/118657689/860d2b5c-e27b-4b86-baf3-c27e0a060228)

### 테스트 코드

![image](https://github.com/clean17/docker-deploy/assets/118657689/619e36e2-6ece-4c64-8faa-d5f5fb6ac08a)

### 도커 이미지로 실행

![image](https://github.com/clean17/docker-deploy/assets/118657689/e2efb49b-34d1-4ee4-8765-611ecdb98bf5)

### CI/CD
![image](https://github.com/clean17/docker-deploy/assets/118657689/38cae805-5ce7-474f-aabc-353d93fea861)



<br>


> ## 정리

- 리액트로 로그인, 회원가입, 할일 목록 화면
- 스프링부트로 TO CRUD 추가
- 스프링시큐리티로 JWT 토큰 인증/인가, CORS 변경
- JPA로 DB에 질의
- JUnit으로 JPA테스트, MOCK테스트, 통합테스트 완료
- 통합테스트 성공시 Rest Dosc 생성 -> Asciidoctor 문서 -> :8080/api.html 확인
- 데이터 검증은 `spring-boot-starter-validation` 이용
- 유효하지 않은 접근시 익셉션 -> 어드바이스로 핸들링
- DB는 도커로 생성, 추후 RDS 변경 예정
- 도커 환경에서 리액트 + 서버 + DB 테스트
- docker-compose 로 한번에 이미지 빌드하고 실행
- Github Actions + DockerHub 로 CI/CD 파이프라인 구축 ( 푸쉬하면 새 이미지 빌드 -> 도커허브 푸쉬 -> AWS에서 이미지 다운 받아 배포 )
- EC2 배포 후, EB에 변경할 예정
- 도메인 구매후 ssh 인증서 추가할 예정

<br>

> ## 이슈
