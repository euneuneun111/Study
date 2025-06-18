package quiz;

public class Quiz01 {

    public static void main(String[] args) {

        // 8줄 반복
        for (int j = 0; j < 8; j++) {
            // 각 줄마다 j+1개의 별 출력
            for (int i = 0; i < j + 1; i++) {
                System.out.print('*');  // 별 출력 (줄 바꿈 없이)
            }

            System.out.println(); // 한 줄 끝나면 줄 바꿈
        }
    }
}
