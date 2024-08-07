package day02;
import java.util.Scanner;

// 구구단
public class Test5 { 

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in); 
		
		int a,b;
		
		/*
		System.out.println("구구단 입력");
		b = scan.nextInt(); // 메뉴 선택
		*/
		
		for (a = 1 ; a < 10; a++ ) {
			System.out.println( a + "단");
		for (b = 1 ; b < 10; b++) {
			System.out.println( a + "x" + b + "=" + a * b);
		}
		}
		
	}

}
