Custom MVC Framework 구현
===

# 목차
- Step1. Reflection API 학습 내용 정리
- Step2. Custom MVC Framework의 DI 구현을 위한 Reflection API 학습
- Step3. FrontController pattern 학습 내용 정리
- Step4. DispatcherServlet, HandlerMapping, HandlerAdapter, ViewResolver를 이용한 Custom MVC Framework 구현

## 학습 내용
### Step1. Reflection API 학습 및 내용 정리
#### Reflection
- JVM 클래스 로더는 클래스 파일에 대한 로딩이 끝나면 클래스 타입의 객체를 생성해서 메모리 힙 영역에 저장하는데,
- 이 때 런타임 시점에 동적으로 힙 영역에 로드된 특정 클래스 타입의 객체를 통해 필드/메서드/생성자를 접근 제어자 상관없이 사용할 수 있도록 지원하는 API
- 힙 영역에 로드된 클래스 타입의 객체를 통해 필드/메소드/생성자를 접근 제어자 상관 없이 사용할 수 있도록 지원하는 API
  - TargetClass.class
  - instance.getClass()
  - Class.forName("Target class")
  - JVM 클래스 로더는 클래스 파일에 대한 로딩이 끝나면 클래스 타입의 객체를 생성해서 메모리 힙 영역에 저장
- 컴파일 시점이 아닌 런타임 시점에 동적으로 특정 클래스의 정보를 추출해낼 수 있는 프로그래밍 기법
- 주로 프레임워크 또는 라이브러리 개발 시 사용됨

#### Reflection을 사용하는 프레임워크 및 라이브러리 소개
- Spring Framework의 DI 처리 부
- JUnit Test Framework
- JSON Serialization/Deserialization 라이브러리 (ex. Jackson)

### Step2. Custom MVC Framework의 DI 구현을 위한 Reflection API 학습
- [x] Reflection API 및 Custom MVC Framework 실습을 위한 프로젝트 생성 및 의존성 추가
```kotlin
dependencies {
    implementation("org.reflections:reflections:0.9.12")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.assertj:assertj-core:3.25.3")
}
```
- [x] Reflection API를 활용하여 Class 정보를 조회하는 세 가지 방법 학습
  - [x] 힙 영역에 생성된 객체로 부터 Class 정보 조회
  - [x] 생성된 인스턴스로 부터 Class 정보 조회
  - [x] CLass.forName()을 이용하여 특정 경로에 위치한 Class 정보 조회
  - [x] id와 name을 속성을 가지는 User객체를 설계하고, Reflection을 이용하여 User class의 생성자와 모든 필드를 출력
- [x] Web Layer를 표현하는 Custom Annotation(@Controller, @Service)을 구현하고, Reflection을 이용하여 Custom Annotation이 표기된 모든 Class명을 출력

### Step3. FrontController pattern 학습 내용 정리
#### FrontController
- 모든 요청을 단일 Handler(처리기)에서 처리 하도록 하는 패턴
- 스프링 웹 MVC 프레임워크의 DispatcherServlet이 프런트 컨트롤러 패턴으로 구현되어 있음
    - DispatcherServlet은 모든 웹 요청을 수신하여, 적절한 Controller로 위임하는 프런트 컨트롤러 역할 수행

#### Forward vs Redirect
##### Forward
- 서블릿에서 클라이언트(웹 브라우저)를 거치지 않고 바로 다른 서블릿(또는 JSP)에게 요청하는 방식
- Forward 방식은 서버 내부에서 일어나는 요청이기 때문에 HttpServletRequest, HttpServletResponse 객체가 새롭게 생성되지 않음(공유됨)
- Forward 전달 방식 : Request 객체로부터 RequestDispatcher 객체를 얻어 포워드할 서블릿 또는 JSP를 명시한 후, request와 response를 같이 반환
```JAVA
RequestDispatcher dispatcher = request.getRequestDispatcher("포워드 할 서블릿 또는 JSP");
dispacher.forward(request, response);
```
##### Redirect
- 서블릿이 클라이언트(웹 브라우저)를 거쳐 다른 서블릿(또는 JSP)에게 요청하는 방식
- Redirect 방식은 클라이언트로 부터 새로운 요청이기 때문에 새로운 HttpServletRequest, HttpServletResponse 객체가 생성됨
- HttpServletResponse 객체의 sndRedirect() 이용

### Step4. Spring MVC Framework의 요청을 처리 과정과 주요 개념에 대한 이해
#### Spring MVC Framework의 요청 처리 과정
![spring_web_mvc_request_processing_workflow.png](docs%2Fresources%2Fspring_web_mvc_request_processing_workflow.png)

1. DispatcherServlet이 모든 웹 요청을 수신
2. DispatcherServlet은 RequestHandlerMapping에게 수신한 요청을 처리할 Handler 선별 작업을 위임
3. RequestHandlerMapping은 요청 URL Path와 HttpMethod 정보를 이용하여 Controller Type 혹은 @Controller가 명시된 Handler 중 적절한 Handler를 선별 후 DispatcherServlet 에게 반환
4. RequestHandlerMapping으로 부터 선별된 Handler를 반환 받은 DispatcherServlet은 HandlerAdapter에 해당 Handler 호출을 위임
5. HandlerAdpater는 DispatcherServlet으로 부터 Handler가 Controller Type 혹은 @Controller가 명시된 Handler 인지 여부를 확인하고, Handler를 호출 후 그 수행 결과인 응답 데이터와 반환할 viewName을 ModelAndView 객체로 초기화 하여 DispatcherServlet에게 반환
6. HandlerAdpater로 부터 Handler 처리 결과와 forwarding될 view name이 담긴 ModelAndView를 반환 받은 DispatcherServlet은 화면 rendering 처리를 위해 viewResolver에게 mo
7. DispatcherServlet은 Handler의 처리 결과와 forwarding될 view name을 VIewResolver에게 전달하여 최종 결과를 응답 처리한다.

#### Spring MVC Framework의 수행 역할 이해
- DispatcherServlet : 모든 웹 요청을 수신
- RequestHandlerMapping : DispatcherServlet이 수신한 요청을 처리할 적절한 Handler(Controller) 선별
- HandlerAdapter : 선별된 Handler를 수행하고 그 결과를 반환하기 위해 가공
- ViewResolver : 가공된 Handler 수행 결과와 함께 출력해야 하는 화면 처리

### Step5. DispatcherServlet, HandlerMapping, HandlerAdapter, ViewResolver를 이용한 Custom MVC Framework 구현
- [x] Custom MVC Framework의 DispatcherServlet 구현을 위한 의존성 추가
```kotlin
dependencies {
    implementation ("org.apache.tomcat.embed:tomcat-embed-core:8.5.42")
    implementation ("org.apache.tomcat.embed:tomcat-embed-jasper:8.5.42")

    implementation ("javax.servlet:javax.servlet-api:4.0.1")
    implementation ("javax.servlet:jstl:1.2")

    implementation ("org.reflections:reflections:0.9.12")

    implementation ("ch.qos.logback:logback-classic:1.2.3")

    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation ("org.assertj:assertj-core:3.25.3")
}
```
- [x] Application 구동을 위한 Entry Point 구현 및 설정
  - [x] Embedded Tomcat의 Root Path 및 port 설정
  - [x] Application build artifact 생성 경로 변경
    - Tomcat은 /webapps/WEB-INF 경로에 있는 build된 servlet을 실행하므로, Project 실행 시 build actifact(servelt) 생성 경로를 '/webapps/WEB-INF/'로 변경
    - AS-IS : ..\mvc-practice\`out\production`\classes
    - TO-BE : ..\mvc-practice\`webapps\WEB-INF`\classes
![build_artifact_location_configuration.png](docs%2Fresources%2Fbuild_artifact_location_configuration.png)

- [x] 메인 화면 출력 API 구현
  - [x] Controller 계층 표현과 공통 기능 정의를 위한 Controller interface 구현
  - [x] `localhost:8080` 요청 시 랜더링 될 home.jsp 작성
  - [x] `localhost:8080` 요청 시 home.jsp 페이지로 포워딩 하기 위한 HomeController 작성

- [x] FrontController 역할을 수행하는 DispatcherServlet 구현
  - [x] Tomcat이 실행할 수 있도록 하기 위해 HttpServlet 상속
  - [x] 모든 웹 요청을 수신 할 수 있도록 @WebAdapter를 명시하고, 수신 경로를 "/"로 지정
  - [x] 메인 화면 요청 처리 Handler 선별을 위한 RequestHandlerMapping 호출
  - [x] 요청 처리를 위해 RequestHandlerMapping를 통해 선별된 Handler(HomeController)를 호출
  - [x] HomeController 호출 결과 반환 받은 jsp 파일명 페이지로 포워딩
      - [x] HttpServletRequest 객체의 getRequestDispatcher()에 포워딩할 jsp 파일명 설정
      - [x] HttpServletRequest 객체의 forwarding()를 호출하여 포워딩 처리

- [x] RequestHandlerMapping 구현 : DispatcherServlet가 수신한 요청을 처리할 적절한 Handler 선별
  - [x] 요청을 처리할 적절한 handler를 선별하기 위해 요청 Path와 HttpMethod 정보를 Map<String, Controller>에 저장하는 mapping map 정의
  - [x] mapping map에서 요청 Path와 HttpMethod가 일치하는 Handler 선별 및 반환 부 구현

- [x] 단순 화면 forwarding을 위한 ForwardController 구현
  - 요청 URL Path에 해당하는 화면으로 forwarding 하기 위해 별도 처리 없이 view name만 반환 하는 범용 컨트롤러
  - Controller : ForwardController
  - 기존 RequestHandlerMapping에 등록된 "/" 요청 시 home.jsp를 반환하는 HomeController 제거
  - home.jsp를 단순 forwarding하던 HomeController는 단순 forwarding을 위한 ForwardController로 대체 가능하므로 삭제

- [x] 회원 등록 화면 출력 API 구현
  - ex. localhost:8080/user/form -> user/form.jsp 반환
  - 출력할 회원 가입 화면인 form.jsp 작성 
  - RequestHandlerMapping에 "/user/form/" 요청 시 user/form.jsp를 반환 할 수 있도록 forwardController("/user/form.jsp") 등록

- [x] 회원 등록 API 구현
  - 요청 : POST localhost:8080/users
  - User 객체 정의 : ID, 이름 속성
  - UserRepo 구현 : Map<String, User> userMap에 userId를 key로, User를 value로 하는 in-memory repo 구현
  - Controller: UserCreateController
  - handle :
    - request.getParameter()를 이용해 요청 파라미터 수신
    - 수신한 파라미터를 이용하여 User객체 생성 및 UserRepo에 저장
    - 리다이렉트할 요청 반환 : return redirect:/users
  - RequestHandlerMapping에 /users 요청에 대한 Mapping Handler로 UserCreateController 등록

- [x] 회원 목록 API 구현
  - 요청 : GET localhost:8080/users
  - view : user/list.jsp
  - Controller: UserCreateController
    - handle :
      - 반환할 회원 목록 정보 설정 : request.setAttribute(UserRepo.list())
      - 포워딩할 화면 파일명 반환 : return forward viewname user/list.jsp
  - UserRepo : 회원 목록 조회 부 구현
  - 화면에 User의 각 필드 출력을 위한 getter() 추가

- [x] RequestHandlerMapping 리팩토링
  - 문제 : 요청 URL Path 정보를 key값으로 하여 해당하는 Controller를 선별하는 경우, 요청 URL Path는 동일하지만 HttpMethod가 다른 요청의 경우 추가적인 Controller 등록 및 선별이 불가능
  - 수정 : 요청 URL Path와 요청 HttpMethod를 가지는 HandlerKey 객체를 key값으로 하여 해당하는 Controller를 선별함으로써, 동일한 URL Path를 가지지만 HttpMethod가 다른 요청인 경우 적절한 Controller 등록 및
    선별 가능
    - AS-IS : Map<String, Controller> mappings
      - key : 요청 URL Path
      - value : 해당 하는 Controller
    - TO-BE : Map<HandlerKey, Controller> mappings
      - key : 요청 URL Path와 요청 HttpMethod로 이루어진 HandlerKey
      - value : 해당 하는 Controller

- [x] 회원 등록 이후 redirect 요청 처리 분기 추가
  - 기존 DispatcherServlet의 service()에서 forwarding만 처리하고 있었으므로, redirect에 대한 화면 처리 분기 추가

- [x] 회원 목록 결과 출력 시 한글 처리를 위한 filter 구현

- View 처리를 위한 ViewResolver 구현
  - [x] ViewResolver(Interface)
    - Handler의 처리 결과로 넘겨 받은 view name과 응답 데이터를 이용한 rendering(redirect or forwarding) 처리
    - 기존 DispatcherServlet에서 담당하는 view 처리 및 응답 데이터 설정부를 ViewResolver로 이관
    - [x] JspViewResolver : Handler 처리 결과로 부터 적절한 View를 선별하고, 해당 View에 맞는 rendering을 수행

  - [x] View(Interface)
    - ViewResolver로 부터 선별된 View 타입으로, rendering 방법에 따라 JspView와 RedirectView로 구분 
    - [x] JspView : Controller 처리 결과를 model 객체에 설정한 후, Request 객체를 통해 대상 view로 forwarding
      - [x] 기존 DispatcherServlet에서 View-name을 받아 forwarding 하던 부분을 JspView 객체로 이관
      - [x] 기존 RequestHandlerMapping과 각 Controller에서 rendering할 view name에서 확장자 '.jsp'로 명시하던 부분을 JspView 객체로 이전
    - [x] RedirectView : response 객체를 통해 대상 view로 Redirect
      - [x] HttpServletResponse.sendRedirect()를 이용하여 redirect할 view를 설정
