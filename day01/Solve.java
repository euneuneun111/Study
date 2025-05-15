
import java.util.Scanner; // 사용자 입력을 받기 위한 Scanner 클래스 불러오기
// 맨 아래 정리 있음
// 학생 점수 계산 프로그램 클래스
public class Solve {

	public static void main(String[] args) { // 메인 메서드: 프로그램 시작 지점
		
		int select, person, sum; // 메뉴 선택값(select), 학생 수(person), 점수 합계(sum) 변수 선언
	
		int[] score = new int[5]; // 점수를 저장할 배열 , 기본 크기 5로 초기화

		int max = Integer.MIN_VALUE; // 최고 점수를 저장할 변수, 가장 작은 정수로 초기화
		
		Scanner scan = new Scanner(System.in); // 키보드 입력 준비, scan이라는 변수명에 입력 값 저장 
	
		boolean run = true; // 프로그램 실행 상태 나타냄 boolean -> 참 거짓 판단

        sum = 0; // 점수 합계 초기화
		person = 0; // 학생 수 초기화
		
		
		while(run) { // 19행 run = true 로 인한 프로그램 실행
			// 메뉴 출력
			System.out.println("------------------------------------------------------");
			System.out.println("1. 학생 수 2. 점수 입력 3. 점수 리스트 4. 분석 5. 종료");
			System.out.println("------------------------------------------------------");

			select = scan.nextInt(); // 사용자로부터 메뉴 번호 입력 받기 -> scan.nextInt() scan이라는 변수에 정수(Int) 값 입력 후 select에 저장
		
			if (select == 1) { // 1번 메뉴: 학생 수 입력
				System.out.println("1. 학생 수");
				person = scan.nextInt(); // 학생 수 입력
				score = new int[person]; // 입력받은 학생 수만큼 배열 재생성, 10행 배열의 기본 크기 재조정
			
			} else if (select == 2) { // 2번 메뉴: 점수 입력
				System.out.println("2. 점수 입력");
		
				for (int i = 0; i < person; i++) { // 학생 수만큼 반복
					System.out.println( (i+1) + "번째 학생 점수 : "); // 몇 번째 학생인지 출력
					score[i] = scan.nextInt(); // 점수 입력 받아 배열에 저장
					sum += score[i]; // 점수를 누적합에 추가
				}
			} else if (select == 3) { // 3번 메뉴: 점수 리스트 출력
				System.out.println("3. 점수 리스트");
			
				for (int i = 0; i < person; i++) { // 학생 수만큼 반복
					System.out.println(score[i]); // 각 학생의 점수 출력
				}
				
			} else if (select == 4) { // 4번 메뉴: 분석 (총합, 평균, 최고 점수)
				System.out.println("4. 분석");
				
				System.out.println("총합 : ");
				System.out.println(sum); // 점수 총합 출력
				
				System.out.println("평균 : ");
				System.out.println(sum / score.length); // 평균 출력 (총합 / 학생 수) , 총합 / 배열의 총 길이
				
				for (int i : score) { // 향상된 for문 - 0부터 score 배열 길이까지 반복 :: for (int i = 0; i < score.length; i++) 동일한 문법
				    max = Math.max(max, i); // 현재 최고 점수보다 크면 max를 갱신
				}
				
				System.out.println("최고 점수 : " + max); // 최고 점수 출력
				
			} else if (select == 5) { // 5번 메뉴: 프로그램 종료
				System.out.println("프로그램 종료");
				break; // 반복문 종료 - while 문 종료
				
			} else { // 잘못된 메뉴 번호 입력 시
				System.out.println("다시 시도해 주십시오"); // 에러 메시지 출력 
			}
		}
	}
}


/* 	
8행 int select, person, sum;  
  - 정수형 변수 3개를 한 줄에 선언한 것

10행 int[] score = new int[5];  
  - 정수형 데이터를 담는 배열 변수 score 선언  
  - 배열 크기는 5로 고정됨
  - 이후 사용된 person = scan.nextInt(); // 학생 수 입력
						score = new int[person]; // 학생 수 만큼의 배열 크기 재선언

18행 boolean run = true;  
  - run 변수에에 참(true) 또는 거짓(false)을 저장  
  - 여기서는 true로 초기화해서 프로그램이 실행 상태임을 나타냄

24행 while(run) { ... }  
  - run이 true일 동안 반복문 실행  
  - run이 false가 되면 반복문 종료

---

import java.util.Scanner;  
Scanner scan = new Scanner(System.in);  
select = scan.nextInt();  
  - Scanner는 키보드 입력을 받기 위한 도구  
  - scan.nextInt()는 정수 입력 받기  
  - 이 외에 nextDouble() (실수), next() (공백 전까지 문자열), nextLine() (한 줄 전체 문자열) 등이 있음  
  - 예) next()는 "동백 꽃 필 무렵" 입력 시 "동백"만 읽음 nextLine()은 "동백 꽃 필 무렵"로 읽음음

---

31행 if(조건), else if(조건), else { ... }  
  - 조건에 따라 코드 흐름 분기  
  - else if는 여러 조건을 차례로 검사할 때 사용  
  - 조건이 두 가지뿐이면 else if 없이 if와 else만 써도 됨

---

61행 for (int i : score) - for 문의 함축 for(int i = 0; i < score.length; i++) { ... } 문법과 동일
  - for (int i = 0 : score) 이런 문법은 에러!  
  
---

68행 break;  
  - 현재 반복문을 즉시 종료

continue;  
  - 현재 반복문의 해당 조건을 건너뛰고 다음 조건으로 넘어감 

ex)	for (int i = 1; i <= 5; i++) {
    if (i == 3) {
        continue; // i가 3일 때는 아래 코드를 건너뛰고 다음 반복으로!
		ㅁㅁㅁㅁ  <- 이 라인부터 아래 코드라는 거임
    }
    System.out.println(i); 
	}  // 출력 값 1 2 4 5
*/ 	