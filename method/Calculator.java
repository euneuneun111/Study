package method;

public class Calculator {

    public static void main(String[] args) {
        int x = 10;
        int y = 20;

        // double 타입 매개변수 2개 더하기 호출
        System.out.println(sum(1.4, 1.5));  

        // 정수 뺄셈 호출
        System.out.println(sub(x, y));       

        // 정수 곱셈 호출
        System.out.println(multi(x, y));     

        // 정수 나눗셈 호출, 결과는 float
        System.out.println(div(x, y));       
    }

    // 1개의 정수 매개변수를 받아 두 배로 더하기
    public static int sum(int a) {
        return a + a;
    } // 더하기

    // 2개의 정수 매개변수 더하기
    public static int sum(int a, int b) {
        return a + b;
    } // 더하기

    // 2개의 float 매개변수 더하기 (오버로딩)
    public static float sum(float a, float b) {
        return a + b;
    } // 더하기

    // 2개의 double 매개변수 더하기 (오버로딩)
    public static double sum(double a, double b) {
        return a + b;
    } // 더하기

    // 3개의 정수 매개변수 더하기 (오버로딩)
    public static int sum(int a, int b, int c) {
        return a + b + c;
    } // 더하기

    // 2개의 정수 빼기
    public static int sub(int a, int b) {
        return a - b;
    } // 빼기

    // 2개의 정수 곱하기
    public static int multi(int a, int b) {
        return a * b;
    } // 곱하기

    // 2개의 정수 나누기 (결과는 float)
    public static float div(int a, int b) {
        return a / (float) b;  // 정수 나눗셈을 float로 변환
    } // 나누기
}
