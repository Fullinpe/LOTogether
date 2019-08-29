package com.example.lotogether;

import android.util.Log;

import java.sql.*;

class DBUtils {

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://10.22.39.84:3306/together?serverTimezone=UTC";

    // 数据库的用户名与密码，需要根据自己的设置
    private static final String USER = "customer";
    private static final String PASS = "9876541233";

    static String[][] connect_mysql(String sql,String... strings) {
        String[][] reStrs = null;
        int x=0;
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            Log.d("TAGG","连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            Log.d("TAGG","实例化Statement对象...");
            stmt = conn.createStatement();
            if(sql.equals(""))
                sql = "SELECT * FROM members";

            ResultSet rs = stmt.executeQuery(sql);

            rs.last();
            reStrs=new String[rs.getRow()][strings.length];
            rs.beforeFirst();

            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                for (String string : strings) reStrs[x][0] = rs.getString(string);
                x++;

            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }// 处理 Class.forName 错误
        finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException ignored){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return reStrs;
    }

}
