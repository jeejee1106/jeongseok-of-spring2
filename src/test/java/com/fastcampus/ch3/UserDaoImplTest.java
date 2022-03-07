package com.fastcampus.ch3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
class UserDaoImplTest {

    @Autowired
    UserDao userDao;

    @Test
    void deleteUser() {
    }

    @Test
    void selectUser() {
    }

    @Test
    void insertUser() {
    }

    @Test
    void updateUser() throws Exception{
        Calendar cal = Calendar.getInstance();
        cal.clear(); //clear를 해줘야 모든 필드가 초기화가 돼서 시간 필드도 초기화가 된다.
        cal.set(2022, 3, 7); // 초기화 된 상태에서 날짜만 넣어주기

        userDao.deleteAll();
        User user = new User("kmj2", "1234", "밍밍", "aaa@aaa.com", new Date(cal.getTimeInMillis()), "instagram", new Date());
        int rowCount = userDao.insertUser(user);
        assertTrue(rowCount==1);

        user.setPwd("4321");
        user.setEmail("bbb@bbb.bbb");
        rowCount = userDao.updateUser(user);
        assertTrue(rowCount==1);

        User user2 = userDao.selectUser(user.getId());
        assertTrue(user.equals(user2));
    }
}