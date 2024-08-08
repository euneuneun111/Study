package day02;
import java.util.Scanner;

// 짝수출력
public class Test9 { 

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int a;
		/*
		Scanner scan = new Scanner(System.in);
		String a;
		a = scan.next();
		*/
		
		for(a = 0; a <=10; a++) {
			if (a % 2 != 0 ) {
				continue;
			}
			System.out.println(a);
		}
		
	}

}
