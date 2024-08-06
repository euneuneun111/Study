package day01;

import java.util.Scanner; // 입력 

public class VarTest1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in); // 생성자의 이름은 클래스 명과 같아야 한다.
		
		
		int year, present;
		
		System.out.println("현재 연도을 입력하세요 : ");
		present = scan.nextInt(); // 문자열 읽기
		
		System.out.println("출생 연도을 입력하세요 : ");
		year = scan.nextInt(); // 문자열 읽기
		
		System.out.println("만 나이 : ");
		System.out.println(present - year );
		System.out.println("한국 나이 : ");
		System.out.println(present - year + 1 );
	
		
		
	}

}
