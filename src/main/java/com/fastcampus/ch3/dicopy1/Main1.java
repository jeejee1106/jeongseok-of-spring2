package com.fastcampus.ch3.dicopy1;

import java.io.FileReader;
import java.util.Properties;

class Car{}
class SportsCar extends Car{}
class Truck extends Car{}
class Engine{}

public class Main1 {
    public static void main(String[] args) throws Exception {
        Car car = (Car) getObject("car");
        Engine engine = (Engine) getObject("engine");
        System.out.println("car = " + car); //config.txt 파일에서 설정하는대로 SportsCar, Truck이 결정된다.
        System.out.println("engine = " + engine);
    }

    static Car getCar() throws Exception {
        Properties p = new Properties();
        p.load(new FileReader("config.txt"));

        Class clazz = Class.forName(p.getProperty("car")); // key가 car인 Entry의 value를 얻어오는 것

        return (Car) clazz.newInstance(); //newInstance가 반환하는 값은 Object이기 때문에 형변환을 해주어야 한다.
    }

    static Object getObject(String key) throws Exception {
        Properties p = new Properties();
        p.load(new FileReader("config.txt"));

        Class clazz = Class.forName(p.getProperty(key)); // key가 car인 Entry의 value를 얻어오는 것

        return clazz.newInstance(); //newInstance가 반환하는 값은 Object이기 때문에 형변환을 해주어야 한다.
    }
}
