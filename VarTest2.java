package day01;

public class VarTest2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int result1 = 10 + 2 + 8;
		System.out.println("result1 : " + result1);
		
		int result2 = 10 + 2 + Integer.parseInt("8"); // Integer.parseInt -> 문자형 인트형으로 잠시 변환
		System.out.println("result2 : " + result2);
		
		String result3 = 10 + "2" + 8;
		System.out.println("result3 : " + result3);
		
		String result4 = "10" + 2 + 8;
		System.out.println("result4 : " + result4);
		
		String result5 = "10" + (2 + 8);
		System.out.println("result5 : " + result5);
	}

}
