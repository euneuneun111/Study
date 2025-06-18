package test;

// 클래스 정의: Operator01 클래스 선언
public class Operator01 {

	public static void main(String[] args) {

		// float 변수 f 선언 및 초기화 (소수점 포함, f 반드시 붙여야 함)
		float f = 123.456f;

		// f에 100을 곱하고 결과를 int로 형변환 → 소수점 이하 절삭
		// 123.456 * 100 = 12345.6 → (int) 12345.6 = 12345
		int i = (int) (f * 100);

		// i에 5를 더하고 다시 100f로 나눔 → 실수 나눗셈
		// (12345 + 5) / 100f = 12350 / 100f = 123.5
		float result = (i + 5) / 100f;

		// 결과 출력: 123.5
		System.out.println(result);

		// 정수 나눗셈 결과 출력 (몫)
		// 12 / 5 = 2 (정수 나눗셈이므로 소수점 이하 버림)
		System.out.println(12 / 5);

		// 정수 나머지 연산 결과 출력
		// 12 % 5 = 2 (12를 5로 나눈 나머지)
		System.out.println(12 % 5);
	}
}
