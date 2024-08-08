package day03;
import java.util.Scanner;

// 계절
public class Test0808_2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		int a;
		
		String[] season = { "Spring", "Summer" , "Fall" , "Winter" } ;
		
		for (a = 0; a<season.length; a++) {
			System.out.println("season" + "[" + a + "]" + " = " + season[a]);
		}
		
		season[1] = "여름";
		System.out.println(season[1]);
		
		for (a = 0; a<season.length; a++) {
			System.out.println("season" + "[" + a + "]" + " = " + season[a]);
		}
		
		
		String[] names = null;
		
		names = new String[] {"가나다", "라마바", "사아자"};
		
		for (a = 0; a<names.length; a++) {
			System.out.println(names[a]);
		}

		
	}

}
