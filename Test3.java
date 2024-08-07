package day02;
import java.util.Scanner;

// 로그인
public class Test3 { 

	public static void main(String[] args) {
		// TODO Auto-generated method stub

Scanner scan = new Scanner(System.in); 
		
		int select;
		String Id;
		
		
		System.out.println("아이디를 입력하세요.");
		Id = scan.next(); // 아이디 입력
		
		if (Id.equals("admin")) {
			System.out.println("관리자로 로그인하셨습니다.");
		} else {
			System.out.println("사용자로 로그인하셨습니다.");
		}
		
		System.out.println("메뉴를 선택하세요");
		select = scan.nextInt(); // 메뉴 선택
		
		System.out.println("사용자 아이디 : " + Id + "메뉴 " + select + "번 선택");
		
		
	}

}
