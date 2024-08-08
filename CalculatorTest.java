package day03;

import java.util.Scanner;

//계산기
public class CalculatorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Calculator cal = new Calculator();
		
		cal.x = 50;
		cal.y = 60;
		
		System.out.println("더하기 : " + cal.add(cal.x, cal.y));
		cal.minus(cal.x, cal.y);
		cal.div();
		System.out.println("곱하기 : " + cal.multi());
		
		
		//정사각형 넓이 구하기
		double result = cal.areaRect(10);
		
		//직사각형 넓이 구하기
		double result2 = cal.areaRect(10, 20);
		
		System.out.println("정사각형 넓이 : " + result);
		System.out.println("직사각형 넓이 : " + result2);

		
	}

}
