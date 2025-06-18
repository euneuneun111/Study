package array;

public class Array_String {

	public static void main(String[] args) {
		// 크기가 5인 문자열 배열 선언
		String[] strArr = new String[5];

		// 배열에 값 대입
		strArr[0] = "Java";
		strArr[1] = "Python";
		strArr[2] = "C++";
		strArr[3] = "JavaScript";
		strArr[4] = "Kotlin";

		// 배열에 저장된 값을 출력
		System.out.println("=== 프로그래밍 언어 목록 ===");
		for (int i = 0; i < strArr.length; i++) {
			System.out.println((i + 1) + ". " + strArr[i]);
		}
	}
}
