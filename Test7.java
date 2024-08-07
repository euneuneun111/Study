package day02;
import java.util.Scanner;

// 1번 누르면 증속 2번 누르면 감속 3번 누르면 중지
public class Test7 { 

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in); 
		
		int speed;
		speed = 20;
		boolean run = true;
		
		while (run) {

				System.out.println("=======================");
				System.out.println("1. 증속 2. 감속 3. 중지");
				System.out.println("=======================");
				System.out.println("선택");
				
				String num = scan.nextLine(); 

			if (num.equals("1")) {
				speed++;
				System.out.println("현재 속도 " + speed);
			} else if (num.equals("2")) {
				speed--;
				System.out.println("현재 속도 " + speed);
			} else if (num.equals("3")) {
				run = false;
			} else {
				System.out.println("유효한 값이 아닙니다.");
			}
			
		
		}
		
	}

}
