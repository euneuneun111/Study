package day03;
import java.util.Scanner;

// 학생 점수 계산
public class Test0808_4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int select, person, sum;
		sum = 0;
		
		person = 0;
		int[] score = new int[5];

		int max = Integer.MIN_VALUE;
		
		Scanner scan = new Scanner(System.in);
		
		boolean run = true;
		
		while(run) {
		System.out.println("------------------------------------------------------");
		System.out.println("1. 학생 수 2. 점수 입력 3. 점수 리스트 4. 분석 5. 종료");
		System.out.println("------------------------------------------------------");

		select = scan.nextInt();
		
		if (select == 1) {
			System.out.println("1. 학생 수");
			person = scan.nextInt();
			score = new int[person];
			
		} else if (select == 2) {
			System.out.println("2. 점수 입력");
		
			for (int i = 0; i < person; i++) {
				System.out.println( (i+1) + "번째 학생 점수 : ");
				score[i] = scan.nextInt();
				sum += score[i];
			}
		} else if (select == 3) {
			System.out.println("3. 점수 리스트");
			
			for (int i = 0; i < person; i++) {
				System.out.println(score[i]);
				
			}
			
		} else if (select == 4) {
			System.out.println("4. 분석");
			
			
				System.out.println("총합 : ");
				System.out.println(sum);
				
				System.out.println("평균 : ");
				System.out.println(sum / score.length);
				
				for (int i : score) {
				    max = Math.max(max, i);
				}
				
				System.out.println("최고 점수 : " + max);
				
			
			
		} else if (select == 5) {
			System.out.println("프로그램 종료");
			break;
			
		} else {
			System.out.println("다시 시도해 주십시오");
		}
}
}
}
