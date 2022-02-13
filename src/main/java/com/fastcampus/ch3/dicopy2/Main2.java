package com.fastcampus.ch3.dicopy2;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// AppContext 이용하기 - 이름으로 객체 검색하기

class Car{}
class SportsCar extends Car{}
class Truck extends Car{}
class Engine{}

class AppContext{
    Map map; //객체 저장소

    AppContext() {
        map = new HashMap();

        try {
            Properties p = new Properties();
            p.load(new FileReader("config.txt"));

            //properties에 저장된 내용을 Map에 저장
            map = new HashMap(p);

            //반복문으로 클래스 이름을 얻어서 객체를 생성해서 다시 map에 저장
            for (Object key : map.keySet()) {
                Class clazz = Class.forName((String)map.get(key));
                map.put(key, clazz.newInstance());
            }

            Class clazz = Class.forName(p.getProperty("car"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Object getBean(String key) {
        return map.get(key);
    }

}

public class Main2 {
    public static void main(String[] args) throws Exception {
        AppContext ac = new AppContext();
        Car car = (Car) ac.getBean("car"); //getBean이 반환하는 것은 Object이기 때문에 형변환이 필요하다.
        Engine engine = (Engine) ac.getBean("engine");
        System.out.println("car = " + car); //config.txt 파일에서 설정하는대로 SportsCar, Truck이 결정된다.
        System.out.println("engine = " + engine);
    }


}