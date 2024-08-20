package day10;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataModel {
	
	

    // 데이터베이스 연결을 위한 필드
    private Connection con = null; // 데이터베이스 연결
    private PreparedStatement pst = null; // SQL 쿼리 실행을 위한 준비된 문
    private ResultSet rs = null; // 쿼리 결과 집합
    private ConnectDB cdb = null; // 데이터베이스 연결을 위한 ConnectDB 인스턴스


    private Users user; // 로그인 사용자 정보 저장
     
    // 생성자: 데이터베이스 연결을 초기화하고, Users 객체를 받아 초기화
    public DataModel(Users user) {
        this.user = user; // 전달받은 Users 객체 저장
        cdb = new ConnectDB(); // ConnectDB 클래스의 인스턴스를 생성
        con = cdb.getCon(); // 데이터베이스 연결을 가져옴
        if (con != null) {
            System.out.println("연결 성공"); // 연결이 성공했을 때
        } else {
            System.out.println("연결 실패"); // 연결이 실패했을 때
        }
    }

    // 게시물 삽입 메소드
    public int insertData(Board board) throws SQLException {
        // INSERT 쿼리 작성
        String sql = "INSERT INTO boards (btitle, bcontent, bwriter, bdate) VALUES (?, ?, ?, ?)";
        pst = con.prepareStatement(sql); // PreparedStatement 객체 생성
        // Board 객체의 필드를 쿼리에 설정
        pst.setString(1, board.getBtitle());
        pst.setString(2, board.getBcontent());
        pst.setString(3, board.getBwriter());
        pst.setDate(4, new java.sql.Date(board.getBdate().getTime()));
        // 쿼리 실행 및 결과 반환
        return pst.executeUpdate();
    }

    // 모든 게시물 조회 메소드
    public List<Board> getAllBoards() throws SQLException {
        List<Board> boardList = new ArrayList<>(); // 게시물 리스트 초기화
        // SELECT 쿼리 작성
        String sql = "SELECT * FROM boards";
        pst = con.prepareStatement(sql); // PreparedStatement 객체 생성
        rs = pst.executeQuery(); // 쿼리 실행 및 결과 집합 반환
        // 결과 집합을 순회하며 Board 객체 생성 후 리스트에 추가
        while (rs.next()) {
            int bno = rs.getInt("bno");
            String btitle = rs.getString("btitle");
            String bcontent = rs.getString("bcontent");
            String bwriter = rs.getString("bwriter");
            Date bdate = rs.getDate("bdate");
            Board board = new Board(bno, btitle, bcontent, bwriter, bdate);
            boardList.add(board);
        }
        return boardList; // 게시물 리스트 반환
    }

    // 특정 게시물 조회 메소드
    public Board read(int bno) throws SQLException {
        // SELECT 쿼리 작성
        String sql = "SELECT bno, btitle, bcontent, bwriter, bdate FROM boards WHERE bno = ?";
        pst = con.prepareStatement(sql); // PreparedStatement 객체 생성
        pst.setInt(1, bno); // 게시물 번호 설정
        rs = pst.executeQuery(); // 쿼리 실행 및 결과 집합 반환
        if (rs.next()) {
            // 결과 집합에서 게시물 정보 추출
            String btitle = rs.getString("btitle");
            String bcontent = rs.getString("bcontent");
            String bwriter = rs.getString("bwriter");
            Date bdate = rs.getDate("bdate");
            return new Board(bno, btitle, bcontent, bwriter, bdate); // Board 객체 반환
        } else {
            System.out.println("해당 번호의 게시물이 없습니다."); // 게시물이 없을 때
            return null; // null 반환
        }
    }

    // 게시물 업데이트 메소드
    public int update(Board board) throws SQLException {
        // UPDATE 쿼리 작성
        String sql = "UPDATE boards SET btitle = ?, bcontent = ?, bwriter = ?, bdate = ? WHERE bno = ?";
        pst = con.prepareStatement(sql); // PreparedStatement 객체 생성
        // Board 객체의 필드를 쿼리에 설정
        pst.setString(1, board.getBtitle());
        pst.setString(2, board.getBcontent());
        pst.setString(3, board.getBwriter());
        pst.setDate(4, new java.sql.Date(board.getBdate().getTime()));
        pst.setInt(5, board.getBno());
        // 쿼리 실행 및 결과 반환
        return pst.executeUpdate();
    }

    // 게시물 삭제 메소드
    public int delete(int bno) throws SQLException {
        // DELETE 쿼리 작성
        String sql = "DELETE FROM boards WHERE bno = ?";
        pst = con.prepareStatement(sql); // PreparedStatement 객체 생성
        pst.setInt(1, bno); // 게시물 번호 설정
        // 쿼리 실행 및 결과 반환
        return pst.executeUpdate();
    }

    // 자원 정리 메소드
    public void closeResources() {
        try {
            if (rs != null) rs.close(); // ResultSet 객체 닫기
            if (pst != null) pst.close(); // PreparedStatement 객체 닫기
            if (con != null) con.close(); // Connection 객체 닫기
        } catch (SQLException e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
        }
    }
}
