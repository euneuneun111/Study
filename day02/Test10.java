package day02;
import java.util.Scanner;
import java.util.Random;

// 가위바위보
public class Test10 { 

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in);
		
		Random rad = new Random();
		
		int i, show;
		
		boolean run = true;
		
	
		while (run) {
		
		System.out.println("1. 가위 2. 바위 3. 보");
		show = scan.nextInt();
		
		if (show == 1) {
			 System.out.println("가위");
			} else if (show == 2) {
				System.out.println("바위");
			} else if (show == 3){
				System.out.println("보");
			} else {
				System.out.println("유효하지 않는 값입니다.");
				System.out.println("종료합니다.");
				break;
			}
	
			int num = rad.nextInt(3) + 1;
			if (num == 1) {
			 System.out.println("가위");
			} else if (num == 2) {
				System.out.println("바위");
			} else {
				System.out.println("보");
			}
			

			if (show == 1 && num == 1) {
		
				System.out.println("비겼습니다.");
			} else if (show == 1 && num == 2) {
				System.out.println("졌습니다.");
			} else if (show == 1 && num == 3) {
				System.out.println("이겼습니다.");
			} else if (show == 2 && num == 1) {
				System.out.println("이겼습니다.");
			} else if (show == 2 && num == 2) {
				System.out.println("비겼습니다.");
			} else if (show == 2 && num == 3) {
				System.out.println("졌습니다.");
			} else if (show == 3 && num == 1) {
				System.out.println("졌습니다.");
			} else if (show == 3 && num == 2) {
				System.out.println("이겼습니다.");
			} else if (show == 3 && num == 3) {
				System.out.println("비겼습니다.");
			} 
		
		
		}
	}

}
