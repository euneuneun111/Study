package day01;

public class VarTest3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 정수형 변수 2개 선언 
		// 문자열 변수 1개
		// 문자형 변수 1개 선언
		// 정수형 변수 1개와 문자열 변수 연결 해서 출력
		
		int i, j;
		i = 1;
		j = 2;
		char val1 = 'A';
		String val2 = "1234";
		
		System.out.println(i + val2 + val1 + j);
		
		//강제 형변환
		
		double k = 3.141545;
		
		j = j + (int)k;
		
		System.out.println("j = " + j + "\nk = " + k);
		
		
		
	}

}
