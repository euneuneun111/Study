package day02;
import java.util.Scanner;

// 메시지 입력 + q 누르면 종료
public class Test8 { 

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in);
		
		String a;
		
		boolean message = true;
		
		while(message) {
		System.out.println("메시지를 입력하세요.");
		a = scan.next();
		
			if (a.equals("q")) {
				message = false;
				System.out.println("프로그램 종료");
			}
			System.out.println("입력한 메시지 : " + a);

		} 
	}

}
