package constructor;

public class Ex01_Main {

    public static void main(String[] args) {
        // 기본 생성자 호출: 모든 필드는 0으로 초기화됨
        Ex01 e1 = new Ex01();
        print(e1);  // 출력: 0 0 0

        // 매개변수 1개 생성자 호출: a=10, b=0, c=0
        Ex01 e2 = new Ex01(10);
        print(e2);  // 출력: 10 0 0

        // 매개변수 3개 생성자 호출: a=10, b=20, c=30
        Ex01 e3 = new Ex01(10, 20, 30);
        print(e3);  // 출력: 10 20 30
    }

    // Ex01 객체의 a, b, c 값 출력 메서드
    public static void print(Ex01 e) {
        System.out.println(e.a);
        System.out.println(e.b);
        System.out.println(e.c);
    }
}

