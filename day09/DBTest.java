package day09;

// JDBC 드라이버 로드
// 데이터베이스 로드

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBTest {

	public static void main(String[] args) {
		Connection con = null;

		try {
			// JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			String url = "jdbc:mysql://localhost:3307/contacts";
			String id = "root";
			String pwd = "vkdlxj7";

			con = DriverManager.getConnection(url, id, pwd);
			System.out.println("DB 연결 성공");

			// PreparedStatement를 사용한 데이터 삽입
			String sql2 = "INSERT INTO person (name, phone, email, age) VALUES (?, ?, ?, ?)";
			PreparedStatement pst = con.prepareStatement(sql2);
			pst.setString(1, "이길동");
			pst.setString(2, "010-1234-1234");
			pst.setString(3, "ㅂㅂㅂㅂ@ㅁㅁㅁㅁ");
			pst.setInt(4, 25); // 나이 매개변수 추가
			
			String sql3 = "INSERT INTO person (name, phone, email, age) VALUES (?, ?, ?, ?)";
			PreparedStatement pst2 = con.prepareStatement(sql3);
			pst.setString(1, "박철홍");
			pst.setString(2, "010-1224-1234");
			pst.setString(3, "ㅂㅈㅈㅂ@ㅁㅁㅁㅁ");
			pst.setInt(4, 25); // 나이 매개변수 추가


			// 데이터 추가 명령 전송
			int result = pst.executeUpdate();
			if (result > 0) {
				System.out.println("데이터 저장 성공");
			} else {
				System.out.println("데이터 저장 실패");
			}

		} catch (Exception e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
		} finally {
			try {
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
			}
		}
	}
}
