package day02;
import java.util.Scanner;

// 참조 변수
public class Test12 { 

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Test12 ref = new Test12();
		Test12 ref2 = new Test12();
		
		if (ref == ref2) {
			System.out.println("동일한 주소를 갖는 객체 입니다.");
		} else {
			System.out.println("서로 다른 주소를 갖는 객체 입니다.");
		}
		
		String name = new String("ㅎㅇ");
		System.out.println(name);
		
		String name2 = "수고했어 오늘도";
		System.out.println(name2);
		System.out.println(name2.length());
		
		int a, b;
		b = 0;
		
		int[] score = new int[5];
		score[0] = 10;
		score[1] = 20;
		score[2] = 30;
		score[3] = 40;
		score[4] = 50;
		
		for (a = 0; a < score.length; a++) {
			b += score[a]; 
		}
		System.out.println("총합 : " + b);

	}

}
