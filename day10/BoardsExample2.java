package day10;

import java.util.List;
import java.util.Scanner;
import java.sql.*;

public class BoardsExample2 {

	private Scanner scan = new Scanner(System.in);
	private DataModel dataModel = new DataModel();
	private Connection con = null;

	public void list() {
		System.out.println();
		System.out.println("게시물 목록");
		System.out.println("================================================");
		// 폭을 명시하여 서식 지정자 수정
		System.out.printf("%-6s %-12s %-401s\n", "no", "writer", "date", "title");
		System.out.println("================================================");

		try {
			List<Board> boards = dataModel.getAllBoards();
			for (Board board : boards) {
				// 폭을 명시하여 서식 지정자 수정
				System.out.printf("%-6d %-12s %-40s\n", board.getBno(), board.getBwriter(), board.getBdate(),
						board.getBtitle());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		mainmenu();
	}

	public void mainmenu() {
		System.out.println();
		System.out.println("================================================");
		System.out.println("메인 메뉴: 1. CREATE 2. READ 3. CLEAR 4. EXIT");
		System.out.println("메뉴 선택");

		String menuNo = scan.nextLine();
		System.out.println();

		switch (menuNo) {
		case "1" -> create();
		case "2" -> read();
		case "3" -> clear();
		case "4" -> exit();
		default -> {
			System.out.println("잘못된 선택입니다. 다시 시도하세요.");
			mainmenu();
		}
		}
	}

	public void create() {
		System.out.println("새 게시물 입력");
		System.out.print("제목: ");
		String title = scan.nextLine();
		System.out.print("내용: ");
		String content = scan.nextLine();
		System.out.print("작성자: ");
		String writer = scan.nextLine();

		Board board = new Board(title, content, writer);
		board.setBdate(new Date(System.currentTimeMillis()));

		try {
			int result = dataModel.insertData(board);
			if (result > 0) {
				System.out.println("데이터가 성공적으로 삽입되었습니다.");
			} else {
				System.out.println("데이터 삽입에 실패했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		list();
	}

	public void read() {
		System.out.println("읽기 메소드 실행");
		System.out.print("읽을 게시물 번호를 입력하세요: ");
		int bno = scan.nextInt();
		scan.nextLine(); // Consume newline

		try {
			Board board = dataModel.read(bno);
			if (board != null) {
				System.out.println("게시물 정보:");
				System.out.println("번호: " + board.getBno());
				System.out.println("제목: " + board.getBtitle());
				System.out.println("내용: " + board.getBcontent());
				System.out.println("작성자: " + board.getBwriter());
				System.out.println("날짜: " + board.getBdate());
			} else {
				System.out.println("해당 번호의 게시물이 존재하지 않습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		subMenu();
	}

	public void subMenu() {
		System.out.println();
		System.out.println("================================================");
		System.out.println("보조 메뉴: 1. Update 2. Delete 3. List");
		System.out.println("메뉴 선택");

		String subMenuNo = scan.nextLine();
		System.out.println();

		switch (subMenuNo) {
		case "1" -> update();
		case "2" -> delete();
		case "3" -> list();
		default -> {
			System.out.println("잘못된 선택입니다. 다시 시도하세요.");
			subMenu();
		}
		}
	}

	public void update() {
		System.out.println("업데이트 메소드 실행");
		System.out.print("업데이트할 게시물 번호: ");
		int bno = scan.nextInt();
		scan.nextLine(); // Consume newline

		try {
			Board board = dataModel.read(bno);
			if (board != null) {
				System.out.print("새 제목: ");
				board.setBtitle(scan.nextLine());
				System.out.print("새 내용: ");
				board.setBcontent(scan.nextLine());
				System.out.print("새 작성자: ");
				board.setBwriter(scan.nextLine());

				System.out.println("================================================");
				System.out.println("보조 메뉴: 1. OK 2. NO ");
				String menuNo = scan.nextLine();

				if ("1".equals(menuNo)) {
					int result = dataModel.update(board);
					if (result > 0) {
						System.out.println("게시물이 성공적으로 업데이트되었습니다.");
					} else {
						System.out.println("게시물 업데이트에 실패했습니다.");
					}
				} else if ("2".equals(menuNo)) {
					System.out.println("업데이트를 취소했습니다.");
				} else {
					System.out.println("잘못된 선택입니다.");
				}
			} else {
				System.out.println("해당 번호의 게시물이 존재하지 않습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		list();
	}

	public void delete() {
		System.out.println("삭제 메소드 실행");
		System.out.print("삭제할 게시물 번호: ");
		int bno = scan.nextInt();
		scan.nextLine(); // Consume newline

		try {
			int result = dataModel.delete(bno);
			if (result > 0) {
				System.out.println("게시물이 성공적으로 삭제되었습니다.");
			} else {
				System.out.println("게시물 삭제에 실패했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		list();
	}

	public void clear() {
		System.out.println("게시물 전체 삭제");
		System.out.println("================================================");
		System.out.println("보조 메뉴: 1. OK 2. NO ");
		System.out.println("메뉴 선택");

		String menuNo = scan.nextLine();

		if ("1".equals(menuNo)) {
			
			try {
				String sql = "TRUNCATE TABLE boards";
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				pstmt.executeUpdate();
				
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if ("2".equals(menuNo)) {
			System.out.println("취소했습니다.");
		} else {
			System.out.println("잘못된 선택입니다.");
		}
		list();
	} 
		
		
	

	public void exit() {
		System.out.println("종료 메소드 실행");
		dataModel.closeResources();
		System.exit(0);
	}

	public static void main(String[] args) {
		BoardsExample2 boardExample = new BoardsExample2();
		boardExample.list();
	}
}
