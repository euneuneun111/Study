package test;

// 클래스 정의: Method_ex 클래스 선언
public class Method_ex {

	// main 메서드: 자바 프로그램의 시작점
	public static void main(String[] args) {

		// main 메서드 시작 알림
		System.out.println("main 시작");

		// sum 메서드 호출 전 출력
		System.out.println("println() 시작");

		// sum(1, 4) 메서드를 호출하고 결과를 출력
		// sum 메서드는 아래에서 정의됨
		System.out.println(sum(1, 4));  // 1 + 4 = 5 출력

		// sum 메서드 호출 후 출력
		System.out.println("println() 종료");

		// main 메서드 종료 알림
		System.out.println("main 종료");
	}

	// 정적 메서드 sum 선언: 두 정수를 받아 더한 값을 반환
	public static int sum(int a, int b) {
		// sum 메서드 시작 알림
		System.out.println("sum() 시작");

		// sum 메서드 종료 알림
		System.out.println("sum() 종료");

		// a와 b의 합 반환
		return a + b;
	}
}
