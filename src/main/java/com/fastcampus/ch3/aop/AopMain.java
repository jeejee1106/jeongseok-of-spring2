package com.fastcampus.ch3.aop;

//AOP개념에 대해 이해해보자!

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AopMain {
    public static void main(String[] args) throws  Exception{
        MyAdvice myAdvice = new MyAdvice();

        Class myClass = Class.forName("com.fastcampus.ch3.aop.MyClass"); //MyClass의 클래스 객체 얻어오기
        Object obj = myClass.newInstance(); //클래스 객체로부터 객체 생성하기

        for (Method m : myClass.getDeclaredMethods()) { //MyClass에 정의된 메서드들을 배열에 담아 for문을 돌려서
            myAdvice.invoke(m, obj, null); //MyAdvice클래스의 invoke메서드에다가 MyClass에 정의된 메서드의 정보를 넘겨준다.
        }


    }
}

//MyClass에 있는 메서드들의 앞, 뒤에 작성될 코드들을 적어준다
class MyAdvice {

    //특정 메서드의 앞, 뒤에만 추가하려면?
    Pattern p = Pattern.compile("a.*"); //정규식을 이용해서 메서드 이름이 a로 시작하는 것만 골라냄!

    //패턴에 맞는 메서드(a로 시작하는 메서드)만 골라서 코드가 추가 되게 하겠다.
    boolean matches(Method m) {
        Matcher matcher = p.matcher(m.getName());
        return matcher.matches();
    }

    void invoke(Method m, Object obj, Object... args) throws Exception {
        if(matches(m)){
            System.out.println("[before]{");
        }

        m.invoke(obj, args); //aaa(), aaa2(), bbb() 메서드를 호출가능하게 함

        if(matches(m)){
            System.out.println("}[after]");
        }
    }
}

class MyClass {
    void aaa() {
        System.out.println("aaa() is called.");
    }
    void aaa2() {
        System.out.println("aaa2() is called.");
    }
    void bbb() {
        System.out.println("bbb() is called.");
    }
}
