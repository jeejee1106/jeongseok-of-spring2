package com.fastcampus.ch3.dicopy1;

import java.io.FileReader;
import java.util.Properties;

//DI흉내내기1 - 다형성과 외부파일을 이용해 객체 생성하기

class Car{}
class SportsCar extends Car{}
class Truck extends Car{}
class Engine{}

public class Main1 {
    public static void main(String[] args) throws Exception {
        //Car car = getCar();
        Car car = (Car) getObject("car"); //Object를 반환하기 때문에 Car로 형변환
        Engine engine = (Engine) getObject("engine"); //같은 메서드로 두 개의 객체를 생성할 수 있다!!
        System.out.println("car = " + car); //config.txt 파일에서 설정하는대로 SportsCar, Truck이 결정된다.
        System.out.println("engine = " + engine);
    }

    static Car getCar() throws Exception {
        Properties p = new Properties();
        p.load(new FileReader("config.txt"));

        Class clazz = Class.forName(p.getProperty("car")); // key가 car인 Entry의 value를 얻어오는 것

        return (Car) clazz.newInstance(); //newInstance가 반환하는 값은 Object이기 때문에 형변환을 해주어야 한다.
    }

    //Car타입 뿐만 아니라 Engine타입도 들어올 수 있다.
    static Object getObject(String key) throws Exception {
        Properties p = new Properties();
        p.load(new FileReader("config.txt"));

        Class clazz = Class.forName(p.getProperty(key)); // key가 car인 Entry의 value를 얻어오는 것

        return clazz.newInstance(); //return 타입이 Object이기 때문에 형변환은 필요없다.
    }
}
