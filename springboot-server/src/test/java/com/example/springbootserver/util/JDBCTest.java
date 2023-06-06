package com.example.springbootserver.util;

import org.springframework.beans.factory.annotation.Value;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class JDBCTest {

    @Value("${meta.port}")
    private static String port;
    @Value("${meta.db}")
    private static String db;
    @Value("${meta.user}")
    private static String user;
    @Value("${meta.pass}")
    private static String pass;

    private static String env = "metacoding";
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 해당 드라이버를 클래스로더에 넘겨준다.
            // 동적으로 자바 클래스를 로딩한다. 드라이버 인스턴스를 생성하여 DriverManager에 등록
//            conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+db+","+user+","+pass);
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+env+"?characterEncoding=utf-8&serverTimezone=Asia/Seoul&useSSL=true&enabledTLSProtocols=TLSv1.2",env,env);
            System.out.println("DB 연결 완료");
            stmt = conn.createStatement();  // Statement 객체 생성  (SQL문을 사용하게 해준다)

            rs = stmt.executeQuery("select * from todos");
            printData(rs); 				// 첫번째 결과

        } catch (ClassNotFoundException e) {
            //	e.printStackTrace();
            System.out.println("JDBC 드라이버 로드 에러");
        }catch (SQLException e) {  // 처음에 빨간줄 떳는데 DriverManager 적으니까 사라짐 맞나 ?
            System.out.println("DB 연결 오류");
        } finally {
            if (stmt !=null) try { stmt.close(); } catch(SQLException e) {}
            if (conn !=null) try { conn.close(); } catch(SQLException e) {}
            // 존재 하지 않는 객체를 닫을수 없으므로 null 인지 먼저 확인
        }
    }

    private static void printData(ResultSet rs) throws SQLException {
        while(rs.next()) {
            System.out.print(rs.getString("id")+"  |  ");
            System.out.print(rs.getString("userId")+"  |  ");
            System.out.print(rs.getString("title")+"  |  ");
            System.out.print(rs.getString("done")+"   ");
//            System.out.println(rs.getString("dept"));
        }
        rs.close();
    }
}