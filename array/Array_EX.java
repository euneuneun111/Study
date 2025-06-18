package array;

public class Array_EX {

	public static void main(String[] args) {
		// 1부터 45까지의 숫자를 담을 배열 생성
		int[] arr = new int[45];

		// 배열에 1부터 45까지의 숫자 할당
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i + 1;
		}

		// 배열 섞기 (셔플 개념) - 배열을 무작위로 섞음
		for (int i = 0; i < arr.length * 3; i++) {
			// 1~44 중에서 무작위 인덱스 생성
			int target = (int)(Math.random() * 44 + 1); // 인덱스 1~44 사이
			int temp = -1;

			// 첫 번째 요소(arr[0])와 무작위 요소(arr[target])를 교환
			temp = arr[0];
			arr[0] = arr[target];
			arr[target] = temp;
		}

		// 섞인 배열에서 앞 6개의 숫자를 출력 (로또 번호 6개)
		for (int i = 0; i < 6; i++) {
			System.out.print(arr[i] + " ");
		}
	}
}
