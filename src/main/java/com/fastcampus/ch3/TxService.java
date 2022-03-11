package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.PublicKey;

@Service
public class TxService {
    @Autowired
    A1Dao a1Dao;
    @Autowired
    B1Dao b1Dao;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertA1WithTx() throws Exception{
        a1Dao.insert(1, 100);
        insertB1WithTx();
        a1Dao.insert(2, 200);
    }

    //REQUIRES_NEW : 새로운 트랜잭션 시작
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insertB1WithTx() throws Exception {
        b1Dao.insert(1, 100);
        b1Dao.insert(2, 200);
    }

    public void insertA1WithoutTx() throws Exception {
        a1Dao.insert(1, 100);
        a1Dao.insert(1, 200);
    }

    @Transactional(rollbackFor = Exception.class)
    //@Transactional만 쓰면 RuntimeException, Error만 rollback한다.
    //rollbakFor 옵션을 써주면 Exception과 그 자손들을 다 잡아서 롤백해준다.
    public void insertA1WithTxFail() throws Exception {
        a1Dao.insert(1, 100);
        a1Dao.insert(1, 200);
    }

    @Transactional
    public void insertA1WithTxSuccess() throws Exception {
        a1Dao.insert(1, 100);
        a1Dao.insert(2, 200);
    }
}
