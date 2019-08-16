package com.example.lotogether;

import android.util.Log;

import java.sql.*;

class DBUtils {

    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
//	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://192.168.137.1:3306/test?serverTimezone=UTC";//?useSSL=false&serverTimezone=UTC";


    // 数据库的用户名与密码，需要根据自己的设置
    private static final String USER = "customer";
    private static final String PASS = "9876541233";

    static String connect_mysql() {
        Connection conn = null;
        Statement stmt = null;
        StringBuilder reStr= new StringBuilder("---\n");
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            //System.out.println("连接数据库...");
            Log.d("TAGG","连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            //System.out.println(" 实例化Statement对象...");
            Log.d("TAGG","实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT 学号,姓名,转入专业 FROM 转专业名单 where 学号 like '17132063%'";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                String id  = rs.getString("学号");
                String name = rs.getString("姓名");
                String url = rs.getString("转入专业");

                // 输出数据
//                System.out.print("学号: " + id);
//                System.out.print(", 姓名: " + name);
//                System.out.print(", 年级: " + url);
//                System.out.print("\n");
                Log.d("TAGG",id+name+url);
                reStr.append(id).append("------").append(name).append("------").append(url).append("\n");


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
        //System.out.println("Goodbye!");
        Log.d("TAGG","Goodbye!");
        return reStr.toString();
    }
}
