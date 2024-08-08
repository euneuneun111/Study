package day02;
import java.util.Scanner;

//3 6 9
public class Test11 { 

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int a;
		/*
		Scanner scan = new Scanner(System.in);
		String a;
		a = scan.next();
		*/
		
		for(a = 1; a <=10; a++) {
			if (a % 3 == 0 ) {
				System.out.println("짝");
				continue;
				
			}
			System.out.println(a);
		}
		
	}

}
