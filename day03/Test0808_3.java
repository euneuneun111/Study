package day03;
import java.util.Scanner;

// 2차원 배열
public class Test0808_3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int [][] mathScores = new int[2][3];
	
		
		System.out.println();
		mathScores[0][0] = 81;
		mathScores[0][1] = 82;
		mathScores[0][2] = 83;
		mathScores[1][0] = 84;
		mathScores[1][1] = 85;
		mathScores[1][2] = 86;
		
		
		for (int i = 0; i < mathScores.length; i++) {
			for (int k = 0; k < mathScores[i].length; k++) {
				System.out.println("수학 점수[" + i + "][" + k + "]: " + mathScores[i][k]);
				
			}
		}
		
		int totalStudent = 0;
		int totalMathSum = 0;
		
		for (int i = 0; i < mathScores.length; i++) {
			totalStudent += mathScores[i].length;
			
			for (int k = 0; k < mathScores[i].length; k++) {
				totalMathSum += mathScores[i][k];
				
			}
		}
		
		double totalMathAvg = (double) totalMathSum / totalStudent;
		System.out.println("전체 학생의 수학 평균 점수 : " + totalMathAvg);
		System.out.println();

		
	}

}
