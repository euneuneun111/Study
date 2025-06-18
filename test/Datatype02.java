package test;

// 클래스 정의: Datatype02 클래스 선언
public class Datatype02 {

   // main 메서드: 자바 프로그램의 시작점
   public static void main(String[] args) {

      // float형 변수 f 선언 및 초기화 (32비트 실수형)
      // float 값은 소수점 뒤에 반드시 f 또는 F를 붙여야 함
      float f = 65.20298f;

      // double형 변수 d 선언 및 초기화 (64비트 실수형)
      // double은 기본 실수형이므로 f를 붙이지 않아도 됨
      double d = 876.765;

      // 변수 f와 d의 값 출력
      System.out.println(f);  // 소수점 이하 7자리까지 표현 (정확도는 떨어질 수 있음)
      System.out.println(d);  // 더 높은 정밀도의 실수 출력
   }
}
