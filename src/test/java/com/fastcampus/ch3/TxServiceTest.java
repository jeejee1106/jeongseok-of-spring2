package com.fastcampus.ch3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
class TxServiceTest {

    @Autowired
    TxService txService;

    @Test
    public void insertA1WithoutTxTest() throws Exception{
//        txService.insertA1WithoutTx();
//        txService.insertA1WithTxSuccess();
//        txService.insertA1WithTxFail();
        txService.insertA1WithTx();
    }

}