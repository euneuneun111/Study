package day09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSelectExample {

	  public static void main(String[] args) {
	        Connection conn = null;
	        PreparedStatement pst = null;
	        ResultSet rs = null;

	        try {
	            // MySQL JDBC 드라이버 로딩
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // 데이터베이스 연결
	            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/contacts", "root", "vkdlxj7");

	            // SQL SELECT 문
	            String sql = "SELECT idusers, usersname, userspassword, usersage, usersemail FROM users";

	            // PreparedStatement 객체 생성
	            pst = conn.prepareStatement(sql);

	            // SQL 명령 실행
	            rs = pst.executeQuery();

	            // 결과 처리
	            while (rs.next()) {
	                // 결과에서 각 열의 값을 추출
	                String idusers = rs.getString("idusers");
	                String usersname = rs.getString("usersname");
	                String userspassword = rs.getString("userspassword");
	                int usersage = rs.getInt("usersage");
	                String usersemail = rs.getString("usersemail");


	                // 결과를 콘솔에 출력
	                System.out.printf("idusers: %s, usersname: %s,userspassword: %s, Age: %d, Email: %s%n", idusers, usersname, userspassword, usersage, usersemail);
	            }
	        } catch (ClassNotFoundException e) {
	            System.out.println("JDBC 드라이버 로딩 실패");
	            e.printStackTrace();
	        } catch (SQLException e) {
	            System.out.println("DB 연결 또는 SQL 오류");
	            e.printStackTrace();
	        } finally {
	            // 자원 해제
	            try {
	                if (rs != null) {
	                    rs.close();
	                }
	                if (pst != null) {
	                    pst.close();
	                }
	                if (conn != null && !conn.isClosed()) {
	                    conn.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
}

/*
public class UserSelectExample {

	  public static void main(String[] args) {
	        Connection conn = null;
	        PreparedStatement pst = null;
	        ResultSet rs = null;

	        try {
	            // MySQL JDBC 드라이버 로딩
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // 데이터베이스 연결
	            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/contacts", "root", "vkdlxj7");
	            

     			// SQL SELECT 문 (홍길동과 같은 이름의 사람 검색)
            	String sql = "SELECT * FROM users WHERE idusers= ?";

	            // PreparedStatement 객체 생성
	            pst = conn.prepareStatement(sql);
	            pst.setString(1, "aaaaa"); // 검색할 이름을 설정

	            // SQL 명령 실행
	            rs = pst.executeQuery();

	            // 결과 처리
	            while (rs.next()) {
	                // 결과에서 각 열의 값을 추출
	                String idusers = rs.getString("idusers");
	                String usersname = rs.getString("usersname");
	                String userspassword = rs.getString("userspassword");
	                int usersage = rs.getInt("usersage");
	                String usersemail = rs.getString("usersemail");


	                // 결과를 콘솔에 출력
	                System.out.printf("idusers: %s, usersname: %s,userspassword: %s, Age: %d, Email: %s%n", idusers, usersname, userspassword, usersage, usersemail);
	            }
	        } catch (ClassNotFoundException e) {
	            System.out.println("JDBC 드라이버 로딩 실패");
	            e.printStackTrace();
	        } catch (SQLException e) {
	            System.out.println("DB 연결 또는 SQL 오류");
	            e.printStackTrace();
	        } finally {
	            // 자원 해제
	            try {
	                if (rs != null) {
	                    rs.close();
	                }
	                if (pst != null) {
	                    pst.close();
	                }
	                if (conn != null && !conn.isClosed()) {
	                    conn.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
}
*/
