package test;

// 클래스 정의: Casting 클래스 선언
public class Casting {

	public static void main(String[] args) {

		// float 값 123f를 int로 형변환 (소수점 없으므로 123 유지)
		int i = (int)(123f);  // 결과: 123

		// 매우 큰 float 값을 int로 강제 형변환 → 데이터 손실 발생
		// 123456873748474f 는 너무 커서 정수 범위 초과 → 쓰레기값(잘린 값) 저장
		int j = (int)(123456873748474f);  // 결과: 쓰레기값 (정확하지 않음)

		// int 값을 byte로 강제 형변환 → byte 범위를 초과해서 오버플로우 발생
		// 478346은 byte(-128~127) 범위를 초과하므로 값이 뒤틀림
		byte b = (byte)(478346);  // 결과: 왜곡된 값 (정확하지 않음)

		// 결과 출력
		System.out.println(i);  // 123
		System.out.println(j);  // 예: -2147483648 같은 이상한 값
		System.out.println(b);  // 예: 106 같은 왜곡된 값

		// byte 타입의 최대값 출력 (상수: 127)
		System.out.println(Byte.MAX_VALUE);  // 127
	}
}
