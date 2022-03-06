package com.fastcampus.ch3;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {

    @Autowired
    DataSource ds;

    @Test //JUnit5는 기본적으로 'no access modifier'를 Public으로 간주하여 public없이 정의 된 테스트 케이스를 허용
    @DisplayName("JDBC연결_테스트")
    void springJdbcConnectionTest() throws Exception{
//        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//        DataSource ds = ac.getBean(DataSource.class); //@Autowired로 자동으로 가져옴!

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn!=null); //assertTrue : 괄호 안의 조건식이 true면 성공
    }

    @Test
    @DisplayName("insert가 잘 될 것인가")
    void insertUserTest() throws Exception{
        deleteAll(); //user테이블에 있는 데이터를 모두 지우는 메서드

        User user = new User("kmj2", "1234", "밍밍", "aaa@aaa.com", new Date(), "instagram", new Date());
        int rowCount = insertUser(user);

        assertTrue(rowCount==1);
    }

    @Test
    @DisplayName("select가 잘 될 것인가")
    void selectUserTest() throws Exception{
        deleteAll(); //user테이블에 있는 데이터를 모두 지우는 메서드

        User user = new User("kmj2", "1234", "밍밍", "aaa@aaa.com", new Date(), "instagram", new Date());
        int rowCount = insertUser(user);

        User user2 = selectUser("kmj2");
        assertTrue(user.getId().equals("kmj2"));
    }

    @Test
    @DisplayName("delete가 잘 될것인가")
    void deleteUserTest() throws Exception {
        deleteAll();
        int rowCount = deleteUser("kmj2");

        assertTrue(rowCount==0); //deleteAll()로 미리 삭제한 후 또 삭제를 하려는 거니까 (즉, 데이터가 없으니까) 삭제되는 데이터는 0개이다.

        User user = new User("kmj2", "1234", "밍밍", "aaa@aaa.com", new Date(), "instagram", new Date());
        rowCount = insertUser(user);
        assertTrue(rowCount==1); //다 지운 후 insert했기 때문에 데이터의 갯수는 1개

        rowCount = deleteUser(user.getId());
        assertTrue(rowCount == 1); //insert한 후 삭제되는 데이터는 1개!

        assertTrue(selectUser(user.getId())==null); //위에서 또 지웠으니까 데이터는 없음! null!
    }

    @Test
    @DisplayName("update가 잘 될 것인가")
    void updateUserTest() throws Exception{
        deleteAll();
        User user = new User("kmj2", "1234", "밍밍", "aaa@aaa.com", new Date(), "instagram", new Date());

        int rowCount = updateUser(user);
        assertTrue(rowCount==0); //데이터가 하나도 없을 때

        insertUser(user);
        rowCount = updateUser(user);
        assertTrue(rowCount==1);

        deleteUser(user.getId());
        rowCount = updateUser(user);
        assertTrue(rowCount==0);
    }


    int insertUser(User user) throws Exception{
        Connection conn = ds.getConnection();

//        String sql = "insert into user_info values ('kmj', '1234', '밍밍', 'aaa@aaa.com', '2022-03-06', 'instagram', now())";
        String sql = "insert into user_info values (?, ?, ?, ?, ?, ?, now())";

        PreparedStatement pstmt = conn.prepareStatement(sql); //SQL Injection공격에 유리(?). 성능향상
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getPwd());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
        pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(6, user.getSns());

        int rowCount = pstmt.executeUpdate(); //executeUpdate : insert, delete, update에 사용
        return rowCount;
    }

    private void deleteAll() throws Exception{
        Connection conn = ds.getConnection();

        String sql = "delete from user_info";

        PreparedStatement pstmt = conn.prepareStatement(sql); //SQL Injection공격에 유리(?). 성능향상
        pstmt.executeUpdate();
    }

    User selectUser(String id) throws Exception{
        Connection conn = ds.getConnection();

        String sql = "select * from user_info where id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql); //SQL Injection공격에 유리(?). 성능향상
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();//executeQuery : select문에 사용

        if(rs.next()){ //만약 rs에 값이 있으면 (id가 있으면!)
            User user = new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(new Date(rs.getDate(5).getTime()));
            user.setSns(rs.getString(6));
            user.setReg_date(new Date(rs.getDate(7).getTime()));

            return user;
        }
        return null;
    }

    int deleteUser(String id) throws Exception{
        Connection conn = ds.getConnection();

        String sql = "delete from user_info where id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql); //SQL Injection공격에 유리(?). 성능향상
        pstmt.setString(1, id);

        return pstmt.executeUpdate();
    }

    //매개변수로 받은 사용자 정보로 user_info테이블을 update하는 메서드
    int updateUser(User user) throws Exception{
        Connection conn = ds.getConnection();

        String sql = "update user_info set email='update@test.test' where id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getId());

        return pstmt.executeUpdate();
    }
}