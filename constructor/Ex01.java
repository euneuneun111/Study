package constructor;

public class Ex01 {

    // 멤버 변수 3개 선언
    int a, b, c;

    // 기본 생성자: 아무 값도 초기화하지 않음
    public Ex01() {}

    // 매개변수 1개 생성자: a만 초기화, b와 c는 0으로
    public Ex01(int a) {
        this(a, 0, 0);  // 다른 생성자 호출
    }

    // 매개변수 2개 생성자: a, b 초기화, c는 0으로
    public Ex01(int a, int b) {
        this(a, b, 0);  // 다른 생성자 호출
    }

    // 매개변수 3개 생성자: a, b, c 모두 초기화
    public Ex01(int a, int b, int c) {
        super();        // 부모 생성자 호출 (Object 클래스)
        this.a = a;     // 멤버 변수에 값 대입
        this.b = b;
        this.c = c;
    }
}

