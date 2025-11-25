package com.evry.bdd.runners;

import com.evry.bdd.utils.DBConnectionUtil;
import java.sql.Connection;

public class DBConnectionTest {
    public static void main(String[] args) {
        Connection conn = DBConnectionUtil.getConnection();
        if (conn != null) {
            System.out.println("🎯 Connection successful!");
        } else {
            System.out.println("🚫 Connection failed!");
        }
    }
}
