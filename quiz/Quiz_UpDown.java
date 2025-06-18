package quiz;

import java.util.Scanner;

public class Quiz_UpDown {
    public static void main(String[] args) {
        int input = 0;
        // 1~100 사이의 랜덤 숫자를 컴퓨터가 결정
        int comNum = (int) (Math.random() * 100 + 1);

        int MAX = 100;  // 입력 가능한 최대값 초기화
        int MIN = 1;    // 입력 가능한 최소값 초기화
        int count = 0;  // 시도 횟수

        final String INTRO = "컴퓨터는 숫자를 결정했습니다.\n시작하겠습니다.\n";

        Scanner scan = new Scanner(System.in);

        System.out.println("******** 업다운 게임 ********\n\n");
        System.out.println(INTRO);

        boolean flagMenu1 = true;  // 게임 반복 여부 플래그
        while (flagMenu1) {
            System.out.println(MIN + "부터 " + MAX + "사이의 숫자를 입력하세요.\n");

            input = Integer.parseInt(scan.nextLine().trim());
            count++;

            if (input != comNum) {
                // 입력값이 정답보다 작으면 최소값 갱신 및 "업" 출력
                if (input >= MIN && input < comNum) {
                    System.out.println("업!!!");
                    MIN = input + 1;
                } 
                // 입력값이 정답보다 크면 최대값 갱신 및 "다운" 출력
                else if (input <= MAX && input > comNum) {
                    System.out.println("다운!!!");
                    MAX = input - 1;
                } 
                // 범위 밖 입력 시 경고 메시지
                else {
                    System.out.println("입력이 올바르지 않습니다.");
                }
                continue;  // 다시 입력 받기
            }

            // 정답을 맞춘 경우
            System.out.println("축하합니다. 정답입니다.");
            System.out.println(count + "번째 맞췄습니다.");

            // 게임 초기화 (새로운 랜덤 수, 범위, 카운트 초기화)
            input = 0;
            comNum = (int) (Math.random() * 100 + 1);
            MAX = 100;
            MIN = 1;
            count = 0;

            // 게임 종료 여부 묻기
            boolean flagMenu2 = true;
            while (flagMenu2) {
                System.out.print("\n\n게임을 종료하시겠습니까? (y/n)\n");
                switch (scan.nextLine().charAt(0)) {
                case 'Y':
                case 'y':
                    System.out.println("게임을 종료합니다.");
                    flagMenu1 = false;  // 메인 루프 종료
                    flagMenu2 = false;  // 메뉴 루프 종료
                    break;
                case 'N':
                case 'n':
                    System.out.println("게임을 계속 진행합니다.\n\n");
                    flagMenu1 = true;   // 게임 계속 진행
                    flagMenu2 = false;  // 메뉴 루프 종료
                    System.out.println(INTRO);
                    break;
                default:
                    System.out.println("메뉴선택이 잘못 되었습니다");
                }
            }
        }
        scan.close();
    }
}
