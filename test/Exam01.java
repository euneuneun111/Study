package test;

// 클래스 정의: Exam01 클래스 선언
public class Exam01 {

	// main 메서드: 자바 프로그램의 시작점
	public static void main(String[] args) {

		// 상수 선언: SPEED는 변경할 수 없는 값 (final 키워드 사용)
		final int SPEED = 20;

		// 일반 변수 선언: count는 값을 변경할 수 있음
		int count = 10;

		// 아래 코드는 주석 처리됨 (비활성화됨)
		// 만약 주석을 제거하면 컴파일 에러 발생 → final 변수는 수정 불가
		// SPEED = 40;

		// count는 일반 변수이므로 값 변경 가능
		count = 11;

		// 문자열 "speed" 출력 (줄바꿈 없음)
		System.out.print("speed");

		// SPEED 값 출력 (줄바꿈 O)
		System.out.println(SPEED); // 20 출력

		// 문자열 "count" 출력 (줄바꿈 없음)
		System.out.print("count");

		// count 값 출력 (줄바꿈 O)
		System.out.println(count); // 11 출력
	}
}
