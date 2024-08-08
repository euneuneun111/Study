package day03;
import java.util.Scanner;

public class Calculator {
	
	Scanner scan = new Scanner(System.in);
	
	int x, y;
	
	static double pi = 3.141592;
	
	
	public Calculator() {
	
	}
	
	// 반환 타입도 있고 매개변수도 있는 메소드 정의
	int add(int x, int y) {
		return x + y;
	}
	
	// 반환 타입은 없고 매개변수도 있는 메소드 정의
	public void minus(int x, int y) {
		System.out.println(x - y);
	}
	
	// 반환 타입은 있고 매개변수는 없는 메소드 정의
	int multi() {
		return x * y;
	}
	
	// 반환 타입은 없고 매개변수는 없는 메소드 정의
	
	void div() {
		System.out.println(x / y);
	}
	
	//정사각형 넓이
	double areaRect(double width) {
		return width * width;
	}
	
	//직사각형 넓이
	double areaRect(double width, double height) {
		return width * height;
	}
	


}
