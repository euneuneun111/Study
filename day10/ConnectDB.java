package day10;

import java.sql.*;

import java.io.*;





public class ConnectDB {
	
	
	static Connection con = null;

	public ConnectDB() {

		try {
			// MySQL JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/contacts", "root", "vkdlxj7");


		} catch (ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로딩 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB 연결 또는 SQL 오류");
			e.printStackTrace();
		}

	}

	Connection getCon() {

		return con;

	}

	
}
