package com.example.config;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {


//    static
//    {
//        try {
//            Class.forName("oracle.jdbc.OracleDriver");
//        } catch (ClassNotFoundException e) {
//            IO.println("Excetion is:"+e);
//        }
//    }

//    public static Connection project_connection() throws SQLException{
//        return DriverManager.getConnection();
//    }


public static Connection getNewConnection() throws SQLException {
    var oracleDataSource=new OracleDataSource();
    oracleDataSource.setURL("jdbc:oracle:thin:@localhost:1521:XE");
    oracleDataSource.setUser("c##scott");
    oracleDataSource.setPassword("tiger");
    return oracleDataSource.getConnection();

}
    public static String handleNull(String value)
    {
        return value==null?"Not null":value;
    }
}
