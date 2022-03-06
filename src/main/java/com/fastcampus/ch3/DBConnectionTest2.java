package com.fastcampus.ch3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;

public class DBConnectionTest2 {
    public static void main(String[] args) throws Exception{
//        // 스키마의 이름(springbasic)이 다른 경우 알맞게 변경
//        String DB_URL = "jdbc:mysql://localhost:3306/springbasic?useUnicode=true&characterEncoding=utf8";
//
//        // DB의 userid와 pwd를 알맞게 변경
//        String DB_USER = "db아이디";
//        String DB_PASSWORD = "db비밀번호";
//        String DB_DRIVER = "com.mysql.jdbc.Driver";
//
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setDriverClassName(DB_DRIVER);
//        ds.setUrl(DB_URL);
//        ds.setUsername(DB_USER);
//        ds.setPassword(DB_PASSWORD);

        /**
         * 위 방법은 DB정보가 바뀔 때 마다 수정 후 컴파일을 다시 해줘야한다.
         * 위 객체에 대한 setter를 property로 처리해주기 위해 root-context.xml에
         * DriverManagerDataSource를 bean으로 등록한다.
         */

        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
        DataSource ds = ac.getBean(DataSource.class);

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
//        assertTrue(conn!=null);
    }
}
