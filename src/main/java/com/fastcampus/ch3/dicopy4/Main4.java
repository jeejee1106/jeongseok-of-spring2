package com.fastcampus.ch3.dicopy4;

import com.google.common.reflect.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//@Autowired 사용하기!!

@Component class Car{
//    @Autowired Engine engine;
//    @Autowired Door door;
    @Resource Engine engine;
    @Resource Door door;

    @Override
    public String toString() {
        return "Car{" + "engine=" + engine + ", door=" + door + '}';
    }
}
@Component class SportsCar extends Car{}
@Component class Truck extends Car{}
@Component class Engine{}
@Component class Door{}

class AppContext{
    Map map; //객체 저장소

    AppContext() {
        map = new HashMap();
        doComponentScan();
        doAutiwired();
        doResource();
    }

    private void doResource() {
        //중요개념! map에 저장된 객체의 iv중에 @Resource가 붙어 있으면 map에서 iv의 이름에 맞는 객체를 찾아서 연결(= 객체의 주소를 iv에 저장)
        try {
            for (Object bean : map.values()) { //map의 value에 저장되어 있는 것들을 가져다가
                for (Field fld : bean.getClass().getDeclaredFields()) {
                    if (fld.getAnnotation(Resource.class) != null) { //value에 만약 @Resource가 어노테이션이 붙어 있으면
                        fld.set(bean, getBean(fld.getName())); //해당하는 이름을 뒤져서 연결해준다. car.engine = obj;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void doAutiwired() {
        //중요개념! map에 저장된 객체의 iv중에 @Autowired가 붙어 있으면 map에서 iv의 타입에 맞는 객체를 찾아서 객체의 주소를 iv에 저장(=객체 연결)
        try {
            for (Object bean : map.values()) { //map의 value에 저장되어 있는 것들을 가져다가
                for (Field fld : bean.getClass().getDeclaredFields()) {
                    if (fld.getAnnotation(Autowired.class) != null) { //value에 만약 @Autowired어노테이션이 붙어 있으면
                        fld.set(bean, getBean(fld.getType())); //해당하는 타입을 뒤져서 bean에 찾은 타입을 넣어준다(연결해준다). car.engine = obj;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void doComponentScan() {
        // 1. 패키지 내의 클래스 목록을 가져온다.
        // 2. 반복문으로 클래스를 하나씩 읽어와서 @Component가 붙어 있는지 확인
        // 3. @Component가 붙어 있으면 객체를 생성해서 map에 저장
        try {
            ClassLoader classLoader = AppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.dicopy4");

            for (ClassPath.ClassInfo classInfo : set) {
                Class clazz = classInfo.load();
                Component component = (Component) clazz.getAnnotation(Component.class);
                if (component != null) {
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName());
                    map.put(id, clazz.newInstance());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Object getBean(String key) { //객체를 검색할 때 byName으로 검색
        return map.get(key);
    }

    Object getBean(Class clazz) { //객체를 검색할 떄 byType으로 검색
        for (Object obj : map.values()) {
            if (clazz.isInstance(obj)) { //obj가 클래스의 객체거나 자손이면 true
                return obj;
            }
        }
        return null;
    }
}

public class Main4 {
    public static void main(String[] args) throws Exception {
        AppContext ac = new AppContext();
        Car car = (Car) ac.getBean("car"); //객체를 검색할 때 byName으로 검색
        Engine engine = (Engine) ac.getBean("engine");
        Door door = (Door) ac.getBean(Door.class); //객체를 검색할 때 byType으로 검색

        //수동으로 객체 연결 (자동은 @Autowired)
//        car.engine = engine;
//        car.door = door;

        System.out.println("car = " + car); //config.txt 파일에서 설정하는대로 SportsCar, Truck이 결정된다.
        System.out.println("engine = " + engine);
        System.out.println("door = " + door);
    }


}
