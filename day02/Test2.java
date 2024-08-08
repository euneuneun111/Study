package day02;
import java.util.Scanner;

//구매자 로그인
public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in); 
		
		int select;
	
		System.out.println("메뉴 선택");
		System.out.println("1번 : 구매자 2번 : 판매자");
		
		System.out.println("메뉴를 선택하세요");
		select = scan.nextInt(); // 점수 입력
		
		if (select == 1) {
			System.out.println("구매자로 로그인하셨습니다.");
		} else if (select == 2) {
			System.out.println("판매자로 로그인하셨습니다.");
		} else {
			System.out.println("유효하지 않은 값입니다.");
		}
		
		
		
	}

}
