package classes;

public class Math_Main {

	public static void main(String[] args) {
		// Math 클래스의 상수 PI 출력 (원주율)
		System.out.println(Math.PI);         // 약 3.141592653589793

		// Math.random() 호출 - 0.0 이상 1.0 미만의 랜덤 실수 출력
		System.out.println(Math.random());   // 예: 0.3745...

		// Math.sin() 함수에 45를 바로 넣으면 라디안이 아니므로 정확한 값 아님
		System.out.println(Math.sin(45));    // 45 라디안 값의 사인 (값은 정확하지 않을 수 있음)

		// Sin 클래스 객체 2개 생성
		Sin s1 = new Sin();
		Sin s2 = new Sin();

		// 각 객체의 angle 값 설정 (인스턴스 변수)
		s1.angle = 60;
		s2.angle = 45;

		// 각 객체의 인스턴스 메서드 호출 - 객체가 가진 angle 값을 사인 계산
		System.out.println(s1.sin());        // s1.angle = 60의 sin 값 (라디안으로 변환 필요)
		System.out.println(s2.sin());        // s2.angle = 45의 sin 값

		// 정적 메서드 호출 - 직접 45를 넣어 사인 계산
		System.out.println(Sin.sin(45));     // 45 라디안의 사인 값

		// 객체를 통해 정적 메서드 호출 (비추천하나 가능)
		System.out.println(s1.sin(45));
		System.out.println(s2.sin(45));
	}
}
