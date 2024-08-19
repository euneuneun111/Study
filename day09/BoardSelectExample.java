package day09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*public class BoardSelectExample {

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
            String sql = "SELECT bno, btitle, bcontent FROM boards";

            // PreparedStatement 객체 생성
            pst = conn.prepareStatement(sql);

            // SQL 명령 실행
            rs = pst.executeQuery();

            // 결과 처리
            while (rs.next()) {
                // 결과에서 각 열의 값을 추출
                String bno = rs.getString("bno");
                String btitle = rs.getString("btitle");
                String bcontent = rs.getString("bcontent");
               

                // 결과를 콘솔에 출력
                System.out.printf("bno: %s, btitle: %s, bcontent: %s", bno, btitle, bcontent);
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
}*/

/*public class BoardSelectExample {

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
			String sql = "SELECT * FROM boards WHERE btitle = ?";

			// PreparedStatement 객체 생성
			pst = conn.prepareStatement(sql);
			pst.setString(1, "감자는"); // 검색할 이름을 설정

			// SQL 명령 실행
			rs = pst.executeQuery();

			// 결과 처리
			while (rs.next()) {
				// 결과에서 각 열의 값을 추출
				String bno = rs.getString("bno");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");

				// 결과를 콘솔에 출력
				System.out.printf("bno: %s, btitle: %s, bcontent: %s", bno, btitle, bcontent);
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
}*/
public class BoardSelectExample {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // MySQL JDBC 드라이버 로딩
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 데이터베이스 연결
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/contacts", "root", "vkdlxj7");

            // SQL SELECT 문 (특정 제목의 게시글 검색)
            String sql = "SELECT * FROM boards WHERE btitle = ?";

            // PreparedStatement 객체 생성
            pst = conn.prepareStatement(sql);
            pst.setString(1, "감자는"); // 검색할 제목을 설정

            // SQL 명령 실행
            rs = pst.executeQuery();

            // 결과 처리
            while (rs.next()) {
                // 결과에서 각 열의 값을 추출하여 Board 객체 생성
                int bno = rs.getInt("bno");
                String btitle = rs.getString("btitle");
                String bcontent = rs.getString("bcontent");

                // Board 객체 생성
                Board board = new Board(bno, btitle, bcontent);

                // 객체의 정보 출력
                System.out.println(board);
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