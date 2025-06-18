package array;

public class Array_EX2 {

	public static void main(String[] args) {

		// 배열에 저장할 초기값 설정 (1부터 시작)
		int value = 1;

		// 3행 3열의 2차원 배열 선언
		int[][] result = new int[3][3];

		// 배열에 값을 채움: 왼쪽 위부터 오른쪽 아래까지 순차적으로
		for (int i = 0; i < result.length; i++) { // 행 반복
			for (int j = 0; j < result[i].length; j++) { // 열 반복
				result[i][j] = value; // 현재 위치에 값 저장
				value++;             // 다음 숫자로 증가
			}
		}

		// 배열 내용 출력
		for (int i = 0; i < result.length; i++) { // 행 반복
			for (int j = 0; j < result[i].length; j++) { // 열 반복
				System.out.print(result[i][j] + " "); // 값 출력
			}
			System.out.println(); // 줄바꿈 (한 행 끝나면)
		}
	}
}
