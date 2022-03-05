package com.fastcampus.ch3.dicopy3;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

// AppContext 이용하기 - Component가 붙으면 객체로 등록하기

@Component class Car{}
@Component class SportsCar extends Car{}
@Component class Truck extends Car{}
@Component class Engine{}

class AppContext{
    Map map; //객체 저장소

    AppContext() {
        map = new HashMap();
        doComponentScan();
    }

    private void doComponentScan() {
        // 1. 패키지 내의 클래스 목록을 가져온다.
        // 2. 반복문으로 클래스를 하나씩 읽어와서 @Component가 붙어 있는지 확인
        // 3. @Component가 붙어 있으면 객체를 생성해서 map에 저장
        try {
            ClassLoader classLoader = AppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.dicopy3"); //getTopLevelClasses : 클래스 목록 가져오는 메서드

            //반복문을 이용해 클래스 정보 가져오기 - 클래스에 컴포넌트 어노테이션이 붙었는지 확인한다.
            for (ClassPath.ClassInfo classInfo : set) {
                Class clazz = classInfo.load();
                Component component = (Component) clazz.getAnnotation(Component.class);
                if (component != null) { //컴포넌트 어노테이션이 붙어있으면
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName()); //StringUtils.uncapitalize : 첫글자를 소문자로 해서
                                                                                     //getSimpleName : 클래스 name을 받아온다.
                    map.put(id, clazz.newInstance()); //객체를 만들어서 map에 저장
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Object getBean(String key) { //객체를 검색할 때 byName으로 검색
        return map.get(key);
    } //객체를 검색할 떄 byName으로 검색

    Object getBean(Class clazz) { //객체를 검색할 떄 byType으로 검색
        for (Object obj : map.values()) {
            if (clazz.isInstance(obj)) { //obj가 클래스의 객체거나 자손이면 true
                return obj;
            }
        }
        return null; //못찾으면 null 반환
    }
}

public class Main3 {
    public static void main(String[] args) throws Exception {
        AppContext ac = new AppContext();
        //AppContext 객체를 생성하면 저장소 map이 만들어지고, doComponentScan()메서드를 호출해서 Component 어노테이션이 붙은 객체들을 map에 저장한다.

        Car car = (Car) ac.getBean("car"); //객체를 검색할 떄 byName으로 검색
        Car car2 = (Car) ac.getBean(Car.class); //객체를 검색할 떄 byType으로 검색
        Engine engine = (Engine) ac.getBean("engine");
        System.out.println("car = " + car); //config.txt 파일에서 설정하는대로 SportsCar, Truck이 결정된다.
        System.out.println("car2 = " + car2); //
        System.out.println("engine = " + engine);
    }


}
