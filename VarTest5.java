package day01;

import java.util.Scanner; // 입력 

public class VarTest5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 4칙 연산을 입력 받아 저장하는 변수
		
		// 2개 정수를 입력받아 저장하는 변수
		
		// + 덧셈, - 뺄셈, / 나눗셈, * 곱셈 , % 나머지 
 		
		Scanner scan = new Scanner(System.in); // 생성자의 이름은 클래스 명과 같아야 한다.
		
		
		int a, b;
		
		
		System.out.println("두 정수를 입력하세요 : ");
		a = scan.nextInt(); // 정수 입력
		b = scan.nextInt(); // 정수 입력
		
		System.out.println("연산을 입력하세요 : ");
		String type = scan.next();
	
		if(type.equals("+")) {
			System.out.println( a + b );
		} else if (type.equals("-")){
			System.out.println( a - b );
		} else if (type.equals("*")) {
			System.out.println( a * b );
		} else if (type.equals("/")) {
			System.out.println( a / b );
		} else if (type.equals("%")) {
			System.out.println( a % b );
		} else {
			System.out.println("유효하지 않는 값입니다.");
		}
			
		
		
		
	}

}
