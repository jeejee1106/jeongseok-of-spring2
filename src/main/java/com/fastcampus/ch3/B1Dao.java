package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class B1Dao {

    @Autowired
    DataSource ds;

    public int insert(int key, int value) throws Exception{
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
//            conn = ds.getConnection();
            conn = DataSourceUtils.getConnection(ds);
            System.out.println("conn = " + conn);
            pstmt = conn.prepareStatement("insert into b1 values(?, ?)");
            pstmt.setInt(1, key);
            pstmt.setInt(2, value);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            close(pstmt);
            DataSourceUtils.releaseConnection(conn, ds); //conn을 무조건 닫는게 아니라 트랜잭션 매니져가 닫을지 말지 판단을 한다.
        }
    }

    private void close(AutoCloseable... acs) {
        for (AutoCloseable ac : acs) {
            try{
                if(ac != null){
                    ac.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void deleteAll() throws Exception{
        Connection conn = DataSourceUtils.getConnection(ds);
        String sql = "delete from b1";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        close(pstmt);
    }
}
