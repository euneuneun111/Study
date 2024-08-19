package day09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoardsInsertExample {

	public static void main(String[] args) {
		Connection conn = null;

		try {
			// MySQL JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/contacts", "root", "vkdlxj7");

			// SQL INSERT 문
			String sql1 = "INSERT INTO boards (btitle, bcontent, bwriter, bdate, bfilename, bfiledata) "
					+ "VALUES (?, ?, ?, NOW(), ?, ?)";

			// PreparedStatement 객체 생성
			PreparedStatement pst1 = conn.prepareStatement(sql1);

			// 매개변수 설정
			pst1.setString(1, "감자는");
			pst1.setString(2, "함박눈");
			pst1.setString(3, "겨울");
			pst1.setString(4, "파일 이름");
			// bfiledata에 대한 값은 빈 BLOB 또는 적절한 InputStream을 설정
			pst1.setBlob(5, (java.io.InputStream) null); // 빈 BLOB 또는 파일 데이터로 교체 가능 파일 경로

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
					System.out.println("데이터베이스 연결 종료");
				}
			} catch (SQLException e) {
				e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
			}
		}
	}
}


