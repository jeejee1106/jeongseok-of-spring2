//package com.fastcampus.ch3;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.GenericXmlApplicationContext;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//

/*
* ApplicationContextTest 클래스와 겹치는 부분이 있어서 주석처리함
* */


//@Component class Engine{} //@Component("engine") id가 생략이 되어있는 것이며, @Component어노테이션은 <bean id="engine" class="패키지.Engine"/> 의 역할과 같다.
//@Component class SuperEngine extends Engine{}
//@Component class TurboEngine extends Engine{}
//@Component class Door{}
//@Component
//class Car{
//    @Value("red")
//    String color;
//    @Value("100")
//    int oil;
//    @Autowired //@Autowired는 byType으로 빈을 찾아서 주입. 같은 타입이 여러개면 이름으로 검색
//    @Qualifier("superEngine") //@Qualifier에 id를 넣어서 지정해줄 수도 있다.
//    Engine engine;
//    @Autowired
//    Door[] doors;
//
//    public Car() {} //생성자를 오버로딩 할 땐 기본 생성자를 꼭 만들어주기.
//
//    public Car(String color, int oil, Engine engine, Door[] doors) {
//        this.color = color;
//        this.oil = oil;
//        this.engine = engine;
//        this.doors = doors;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public void setOil(int oil) {
//        this.oil = oil;
//    }
//
//    public void setEngine(Engine engine) {
//        this.engine = engine;
//    }
//
//    public void setDoors(Door[] doors) {
//        this.doors = doors;
//    }
//    @Override
//    public String toString() {
//        return "Car{" +
//                "color='" + color + '\'' +
//                ", oil=" + oil +
//                ", engine=" + engine +
//                ", doors=" + Arrays.toString(doors) +
//                '}';
//    }
//}
//
//public class SpringDITest {
//    public static void main(String[] args) {
//        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
//
//        //참고
////        Car car2 = (Car) ac.getBean(Car.class); //byName // 아래와 같은 코드
//        Car car2 = ac.getBean("car", Car.class); //byName의 형식에서 뒤에 타입정보까지 같이 써주면 형변환을 안해도 된다.
//        //그리고 이렇게 getBean으로 한 번 생성된 객체는 아래에서 또 만들어도 같은 객체를 만든다. (car=car2 같은 해쉬값)
//        //즉, 한 번 만들어 놓으면 그 후에는 재생성이 아닌 호출의 개념??? 인듯???
//        //그 이유는 기본값이 싱글톤이기 때문이다.(같은 기능을 하는 객체를 여러개 생성할 필요 없음, 참고:싱글톤?클래스의 객체를 하나만 만드는 것)
//        //그래도 (주소값이=해쉬값)다른 객체를 생성하고 싶다면 xml파일에서 scope를 prototype으로 바꿔주면 된다.
//
//        Car car = (Car) ac.getBean("car"); //byName
////        Engine engine = (Engine) ac.getBean(Engine.class); //byName
//        Engine engine = (Engine) ac.getBean("superEngine");
//        Door door = (Door) ac.getBean("door"); //byName
//
//        //setter에 값을 넣어 호출하는 두 가지 방법 중 첫 번째.(두 번째는 config.xml파일에 있다.)
////        car.setColor("red");
////        car.setOil(100);
////        car.setEngine(engine);
////        car.setDoors(new Door[]{ac.getBean("door", Door.class), (Door) ac.getBean("door")}); //두 인덱스 값은 같은 뜻임! 일부로 다르게 써 봄ㅎㅎ
//
////        System.out.println("door = " + door);
////        System.out.println("engine = " + engine);
//        System.out.println("engine = " + engine);
//        System.out.println("car = " + car);
////        System.out.println("car2 = " + car2);
//    }
//}
