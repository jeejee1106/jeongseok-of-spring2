## 1. Bean
- **Bean이란?**
	- JavaBeans에서 시작한 용어. JavaBeans란 재사용 가능한 컴포넌트를 말한다. 후에 bean을 MVC의 model로 사용하면서 bean을 JSP container가 관리했고, 그 다음엔 EJB가 나오면서 EJB container가 bean을 관리했다.
	- 이렇게 점점 발전해서 지금은 Spring Bean이 되었고, 복잡했던 규칙을 단순하게 만들었다. Spring container가 관리!
	- 즉, bean이란 **Spring container가 관리하는 객체**를 말한다.
	- Spring container는 bean의 저장소로서 bean을 저장, 관리(생성, 소멸, 연결)한다.
- **Bean Factory**
	- bean을 생성하고 연결하는 등의 기본 기능을 정의해 놓은 인터페이스.
- **ApplicationContext**
	- Beanfactory를 확장해서 여러 기능을 추가한 인터페이스.
	- ApplicationContext의 구현체는 정말 많다. 2번 목록에서 자세하게 알아보자.

## 2. ApplicationContext
- **ApplicationContext의 주요 메서드**
	- getBeanDefinitionNames() : 정의된 빈의 이름을 배열로 반환
	- getBeanDefinitionCount() : 정의된 빈의 개수를 반환
	- ac.containsBeanDefinition("car") : 해당 빈의 정의가 포함되어 있는지 확인
	- ac.containsBean("car") : 빈이 포함되어 있는지 확인
	- getType("car") : 빈의 이름으로 타입을 확인
	- isSingleton("car") : 빈이 싱글톤인지 확인
	- isPrototype("car") : 빈이 프로토타입인지 확인
	- isTypeMatch("car", Car.class) : "car"라는 이름의 빈의 타입이 Car인지 확인
	- findAnnotationOnBean("car", Component.class) : 빈 car에 @Component가 붙어있으면 반환
	- getBeanNamesForAnnotation(Component.class) : @Component가 붙은 빈의 이름을 배열로 반환
	- getBeanNamesForType(Engine.class) : Engine 타입 또는 그 자손 타입인 빈의 이름을 배열로 반환

## 2. IoC와 DI
- **Ioc**
	- 제어의 역전(Inversion of control). 제어의 흐름을 전통적인 방식과 다르게 뒤바꾸는 것

- **DI**
	- 사용할 객체를 외부에서 주입받는 것

## 3. 스프링 어노테이션
- **@Autowired**
	- Spring Container에서 **타입으로 빈을 검색**해서 참조변수에 자동 주입(DI). 검색된 빈이 n개면 그 중에 참조변수와 이름이 일치하는 것을 주입한다.
	- 인스턴스 변수, setter, 참조형 매개변수를 가진 생성자, 메서드에 적용한다.
	- 주입받을 bean들을 다 선언한 생성자에 붙이는 것이 좋다.
	
	- 주입 대상이 변수일 때, 검색된 빈이 1개가 아니라면 예외가 발생한다.
	- 주입 대상이 배열일 때, 검색된 빈이 n개라도 예외가 발생한다.

**참고 : @Autowired(required=false)일 땐 주입할 빈을 못찾아도 예외가 발생하지 않는다.

- **@Resource**
	- Spring container에서 **이름으로 빈을 검색**해서 참조변수에 자동 주입(DI)한다. 일치하는 이름의 빈이 없으면 예외 발생한다.
	- 이름은 생략가능하다. 이름을 생략하면 참조변수명이 빈의 이름이 된다.

- **@Component**
	- <component-scan>으로 @Component가 붙은 클래스를 자동 검색해서 빈으로 등록한다.
	- 클래스명에서 첫글자를 소문자로해서 빈의 이름으로 등록한다.
	- @Controller 어노테이션 안에 @Component가 들어가 있어서 컨트롤러도 자동으로 빈에 등록이 된다.

- **@Value**
	- 값을 지정할 때 사용

- **@PropertySource**
	- 잘 모르겠다.. 더 공부하기

- **스프링 어노테이션 vs 표준 어노테이션**

|스프링 어노테이션|표준 어노테이션|비고|
|---|---|---|
|@Autowired|@Inject|@Inject에는 required속성이 없음|
|@Qualifier|@Qualifier, @Named|스프링의 @Qualifier는 @Named와 유사|
|x|@Resource|스프링에는 이름 검색이 없음|
|@Scope("singleton")|@Singleton|표준에서는 prototype이 디폴트|
|@Component|@Named, @ManagedBean|표준에서는 반드시 이름이 있어야 함|
	
## 3. DAO의 작성과 적용
- **DAO란?**
	- 데이터에 접근하기 위한 객체(Data Access Object)
	- database에 저장된 데이터의 CRUD를 담당한다.
	- DB테이블 하나당 하나의 DAO를 작성해야한다.
- **작성하는 이유**
	- 중복 코드(중복 쿼리)의 제거

## 4. Transaction
- **transaction**
	- 더 이상 나눌 수 없는 작업의 단위
	- transaction의 속성
		- 원자성(Atomicity) : 나눌 수 없는 하나의 작업으로 다뤄져야한다.
		- 일관성(Consistency) : 트랜잭션 수행 전과 후가 일관된 상태를 유지해야한다.
		- 고립성(Isolation) : 각 트랜잭션은 독립적으로 수행되어야한다.
		- 영속성(Durability) : 성공한 트랜잭션의 결과는 유지되어야한다.

- **커밋(commit)**
	- 작업 내용을 DB에 영구적으로 저장
		- 자동커밋(Auto commit) : 명령 실행 후 자동으로 커밋이 수행(rollback불가)
		- 수동커밋 : 명령 실행 후 명시적으로 commit 또는 rollback을 입력
- **롤백(rollback)**
	- 최근 변경사항을 취소(마지막 커밋으로 복귀)

- **Isolation level**
	- READ UNCOMMITED : 커밋되지 않은 데이터도 읽기 가능
	- READ COMMITED : 커밋된 데이터만 읽기 가능
	- REPEATABLE READ : 트랜잭션이 시작된 이후 변경은 무시됨
	- SERIALIZABLE : 한 번에 하나의 트랜잭션만 독립적으로 수행
	
## 5. AOP의 개념과 용어
- **AOP**
	- Aspect Oriented Programming : 관점 지향 프로그래밍
	- 부가기능=advice(코드!!)을 **동적**으로 추가해주는 기술
	- 동적으로 추가?? 우리가 프로그램을 만들 때 추가해주는 것이 아니라 코드가 실행되면서 자동으로 추가되게 하는 것
	- **메서드의 시작 또는 끝에 자동으로 코드(advice)를 추가한다.**

- **AOP 관련 용어**

|용어|설명|
|---|---|
|target|advice가 추가될 객체|
|advice|target에 동적으로 추가될 부가 기능(코드)|
|join point|advice가 추가(join)될 대상(메서드)|
|pointcut|join point들을 정의한 패턴|
|proxy|target에 advice가 동적으로 추가되어 생성된 객체|
|weaving|target에 advice를 추가해서 proxy를 생성하는 것|

- **Advice의 종류**

|종류|애너테이션|설명|
| --- | --- | --- |
|around advice|@Around|메서드의 시작과 끝 부분에 추가되는 부가 기능|
|before advice|@Before|메서드의 시작 부분에 추가되는 부가 기능|
|after advice|@After|메서드의 끝 부분에 추가되는 부가 기능|
|after returning|@AfterReturning|예외가 발생하지 않았을 때 실행되는 부가 기능(즉, try문에 들어가는 부가 기능)|
|after throwing|@AfterThrowing|예외가 발생했을 때 실행되는 부가 기능 (즉, catch문에 들어가는 부가 기능)|
