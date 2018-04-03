package com.idc.utils;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/7/13.
 */
public class CallProMain {
    public static void main(String[] args) {
        Connection conn = null;
        CallableStatement cstmt = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "orcl_dev",
                    "dev");
            List list = new ArrayList();
            list.add(30);
            list.add(31);
            list.add(32);
            list.add(33);
            ArrayDescriptor tabDesc = ArrayDescriptor.createDescriptor("MSG_ARRAY", conn);
            ARRAY vArray = new ARRAY(tabDesc, conn, list.toArray());
            cstmt = conn.prepareCall("call modifyage(?)");
            cstmt.setArray(1, vArray);
            cstmt.execute();
            cstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
