package test;

// 클래스 정의: Datatype01이라는 이름의 클래스 선언
public class Datatype01 {

   // main 메서드: 자바 프로그램의 시작점
   public static void main(String[] args) {

      // int형 변수 a 선언 및 초기화 (32비트 정수형)
      int a = 10;

      // short형 변수 s 선언 및 초기화 (16비트 정수형)
      short s = 2;

      // byte형 변수 b 선언 및 초기화 (8비트 정수형, -128 ~ 127 범위)
      byte b = 6;

      // long형 변수 l 선언 및 초기화 (64비트 정수형, 큰 숫자를 저장할 수 있음)
      // 정수 값 뒤에 L을 붙여야 long 타입으로 인식됨
      long l = 125362133223L;

      // 각 변수의 값을 출력
      System.out.println(a);  // 10 출력
      System.out.println(s);  // 2 출력
      System.out.println(b);  // 6 출력
      System.out.println(l);  // 125362133223 출력
   }
}
