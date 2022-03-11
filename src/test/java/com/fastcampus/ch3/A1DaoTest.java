package com.fastcampus.ch3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
class A1DaoTest {

    @Autowired
    A1Dao a1Dao;

    @Autowired
    DataSource ds;

    @Autowired
    DataSourceTransactionManager tm;

    @Test
    public void insertTest() throws Exception{
        /** 상황) 키값이 충돌되게 insert를 하면 하나는  insert가 되고, 하나는 안되는 상황이다.
         * 이 떄, 둘 다 성공일 경우에만 insert가 되게 하고 싶다.
         * 그러려면 Transaction을 걸어줘야 한다. 한 번 해보자
         */

        //1. TxManager를 생성
//        PlatformTransactionManager tm = new DataSourceTransactionManager(ds); //DataSourceTransactionManager tm;
        // 2. 트랜잭션 시작
        TransactionStatus status = tm.getTransaction(new DefaultTransactionDefinition());

        try {
            a1Dao.deleteAll();
            a1Dao.insert(1, 100);
            a1Dao.insert(2, 200);
            tm.commit(status); //성공하면 DB커밋
        } catch (Exception e) {
            e.printStackTrace();
            tm.rollback(status); //실패하면 DB롤백
        } finally {
        }
    }

}