# Todo 프로젝트

> ## 개요
 화면을 리액트로 만들고 스프링 서버를 연결시킨 뒤 깃헙액션 + 도커허브 CI/CD 파이프라인을 통해 EB에 배포한다.
 
<a href="http://ec2-13-124-79-248.ap-northeast-2.compute.amazonaws.com">  배포 링크  </a>

<br>

### 화면

![image](https://github.com/clean17/docker-deploy/assets/118657689/b821578d-18d5-453b-b34d-3437855c4c24)

![image](https://github.com/clean17/docker-deploy/assets/118657689/2a7ff880-6e9c-496a-b1e9-b6dc2c43de4e)

### RestDogs 문서 - /api.html
![image](https://github.com/clean17/docker-deploy/assets/118657689/860d2b5c-e27b-4b86-baf3-c27e0a060228)

### 테스트 코드

![image](https://github.com/clean17/docker-deploy/assets/118657689/619e36e2-6ece-4c64-8faa-d5f5fb6ac08a)

### 도커 이미지로 실행 ( 개발 환경 )

![image](https://github.com/clean17/docker-deploy/assets/118657689/e2efb49b-34d1-4ee4-8765-611ecdb98bf5)

### 도커허브

![image](https://github.com/clean17/docker-deploy/assets/118657689/38805195-22d5-445a-aa78-9b951719d923)

### CI/CD
![image](https://github.com/clean17/docker-deploy/assets/118657689/38cae805-5ce7-474f-aabc-353d93fea861)
![image](https://github.com/clean17/docker-deploy/assets/118657689/80ffb238-f7bd-4ec1-852f-1b7867a36677)
![image](https://github.com/clean17/docker-deploy/assets/118657689/de131e3c-56fc-4cbe-a265-a4c451f7dbde)
![image](https://github.com/clean17/docker-deploy/assets/118657689/111a6442-0c8a-4852-ae07-4dfbe9469fb3)

### Exception + 핸들러
![image](https://github.com/clean17/docker-deploy/assets/118657689/50694ee8-4a6e-46c8-88ee-bd830af30203)
![image](https://github.com/clean17/docker-deploy/assets/118657689/380d96ee-19be-4a48-8051-9a496aaee0f8)

### Cors + 시큐리티 + JWT 인증
![image](https://github.com/clean17/docker-deploy/assets/118657689/de643c83-b826-491b-a426-89a6e8eb0a66)
![image](https://github.com/clean17/docker-deploy/assets/118657689/0f45dff1-ecc6-4a0d-95a3-9ec64f6885a6)

<br>


> ## 정리

- 리액트로 로그인, 회원가입, 할일 목록 화면
- 스프링부트로 TODO CRUD 추가
- 스프링시큐리티로 JWT 토큰 인증/인가, CORS 변경
- JPA로 DB에 질의
- JUnit으로 JPA테스트, MOCK테스트, 통합테스트 완료
- 통합테스트 성공시 Rest Dosc 생성 -> Asciidoctor 문서 생성 <br>
  <a href="http://ec2-43-201-97-14.ap-northeast-2.compute.amazonaws.com:8080/api.html"> 문서링크 -> /api.html </a> 확인
- 데이터 검증은 `spring-boot-starter-validation` 이용 ( `@Valid` )
- 유효하지 않은 접근시 익셉션(400, 401, 403, 404, 500) -> 어드바이스로 핸들링
- 도커 환경에서 리액트, 서버, DB 테스트 
- `docker-compose`, `Dockerfile` 이미지 빌드하고 실행
- Github Actions + DockerHub 로 CI/CD 파이프라인 구축 ( 푸쉬하면 새 이미지 빌드 -> 도커허브 푸쉬 -> AWS에서 이미지 다운 받아 배포 )

<br>

> ## 이슈

- Gradle 에서 implementation, compileOnly, runtimeOnly, testImplementation를 제외한 종속성을 사용한다면 configurations 에 추가해서 설정해야 한다. <br>
 ![image](https://github.com/clean17/docker-deploy/assets/118657689/e0bd4516-9d9d-4461-9268-defbf317d94a)
- clean build시 RestDocs 문서가 이전 빌드를 보는 이유는 복사 위치를 /build 가 아닌 /src 에 했기 때문이다.
- 도커로 DB를 볼륨으로 만들었을 경우 이미지를 다시 만들어도 변경사항이 적용되지 않는다. <br>
컨테이너에 종속되지 않는 볼륨은 새로 만들던지 `docker-compose down -v`, 터미널에서 직접 스키마를 수정한다.
```
docker exec -it [container_id_or_name] bash 
mysql -u [username] -p
```
- MySql 8.x 버전이 아니라면 utf-8 설정을 직접 해줘야 한다. 도커에서는 `.cnf` 파일로 데이터베이스 설정을 수정한다.
- boolean 타입을 jpa 에서 매핑하려면  `@column(columnDefinition = "TINYINT(1)")`  을 붙여서 에러를 피한다.
- `docker-compose` 에서 환경변수를 연결할때는 `Dockerfile`에서 한번 더 연결 시켜 스크립트에 전달한다.
- 도커 컨테이너로 DB와 서버를 실행시키면 DB의 호스트이름은 도커의 서비스 이름이 된다.
- vsCode 에서는 파일이나 폴더명의 대소문자를 변경해도 커밋에 담기지 않을때가 있다. 이럴때는 인텔리를 이용하자.
- 로컬에서 DB를 생성할때는 `use [데이터베이스]` 를 사용하지 않아도 되지만 배포시에는 사용해야 한다.
- `burnett01/rsync-deployments`으로 프로젝트를 EC2에 복사한 뒤에 이미지를 빌드했지만 프리티어는 성능이 별로라 너무 오래 걸린다.
Github Actions의 워크플로우에서 이미지를 빌드한 후 도커허브에 푸쉬하고 EC2에서는 이미지를 다운받아 실행만 한다.
- 워크플로우에서 이미지의 태그를 변경했지만 EC2에서 `docker-compose` 로 실행할때는 yml에 변경된 태그로 이미지가 매칭되어야 한다.
- EC2에서 다운받은 이미지를 도커 데몬이 관리하기 때문에 이미지 경로를 찾을 필요가 없다.
- `docker-compose` 에 환경변수를 넣을때는 `> .env / >> .env` 를 이용한다. ( 두번째는 추가 )
