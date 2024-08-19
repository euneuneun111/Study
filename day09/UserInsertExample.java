package day09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserInsertExample {

	public static void main(String[] args) {
		Connection conn = null;

		try {
			// MySQL JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/contacts", "root", "vkdlxj7");

			// SQL INSERT 문
			String sql1 = "INSERT INTO users (idusers, usersname, userspassword, usersage, usersemail) VALUES (?, ?, ?, ?, ?)";

			// PreparedStatement 객체 생성
			PreparedStatement pst1 = conn.prepareStatement(sql1);
			pst1.setString(1, "park.1");
			pst1.setString(2, "박철홍");
			pst1.setString(3, "1234");
			pst1.setInt(4, 25); // 나이 매개변수 추가
			pst1.setString(5, "ㅂㅈㅈㅂ@ㅁㅁㅁㅁ");

			// SQL 명령 실행
			int result = pst1.executeUpdate();
			if (result > 0) {
				System.out.println("데이터 저장 성공");
			} else {
				System.out.println("데이터 저장 실패");
			}

			// PreparedStatement 닫기
			pst1.close();
		} catch (Exception e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
			}
		}
	}
}
