Url Shortener 과제입니다.

### 실행
- (프로젝트 경로에서) $ mvn spring-boot:run
- $(USER_HOME)/h2db 경로 내에 h2 file database가 생성됩니다.


#### API
- POST /shorten-url : Original URL을 입력받고 Shorten URL을 반환합니다. UI를 통해 접근 가능합니다.
- GET /shorten-url : Shorten URL 목록과 요청되었던 redirection count를 반환합니다. 요청횟수의 역순으로 정렬됩니다. (UI 없음)


#### 개발환경
- Spring Boot 2.2.6(새 프로젝트 생성시 기본값)
- Spring data JPA
- Apache commons lang
- Freemarker
- React (cdn 사용)
- axios (cdn 사용)
