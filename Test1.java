package day02;
import java.util.Scanner;

//학점 계산
public class Test1 { 

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int point;
		String grade;

		Scanner scan = new Scanner(System.in); 
		
		System.out.println("점수를 입력하세요.");
		point = scan.nextInt(); // 점수 입력
		
		if (point >= 90) {
			grade = "A+";
			
		} else if (point >= 80) {
			grade = "A";
		} else if (point >= 70) {
			grade = "B+";
		} else if (point >= 60) {
			grade = "B";
		} else if (point >= 50) {
			grade = "C+";
		} else if (point >= 40) {
			grade = "C";
		} else if (point >= 30) {
			grade = "D";
		} else {
			grade = "F";
		}
		
		System.out.println("점수 : " + point + "\n학점 : " + grade);
			
	}

}
