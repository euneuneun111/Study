package day02;
import java.util.Scanner;

// 1~100까지 짝수들의 합
public class Test4 { 

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in); 
		
		int a,sum;
		sum = 0;
		
		/*
		System.out.println("구구단 입력");
		b = scan.nextInt(); // 메뉴 선택
		*/
		
		for (a = 0 ; a <= 100; a++ ) {
			if (a % 2 == 0) {
				sum = sum + a;
			} 
		}
		
		System.out.println(sum);
		
	}

}
