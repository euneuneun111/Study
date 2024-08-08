package day02;
import java.util.Scanner;

// while 3되면 스탑
public class Test6 { 

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in); 
		
		int a, sum;
		a = 20;
		sum = 0;
		/*
		System.out.println("구구단 입력");
		b = scan.nextInt(); // 메뉴 선택
		*/
		
		while (a > 0) {
			System.out.println(a);
			if (a == 3 ) {
				System.out.println("멈추었습니다.");
				break;
			}
			a--;
		}
		
	}

}
