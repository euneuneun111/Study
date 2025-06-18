package classes;

public class Card {

    // 클래스(static) 변수: 모든 카드 객체가 공유하는 크기 정보
    static int WIDTH = 100;
    static int HEIGHT = 200;

    // 인스턴스 변수: 각 카드 객체가 고유하게 가지는 속성
    String kind; // 카드 종류 (예: "Spade", "Heart" 등)
    int num;     // 카드 숫자 (예: 1 ~ 13)

    /**
     * 생성자(Constructor)
     * kind와 num 값을 매개변수로 받아 카드 객체 초기화
     */
    public Card(String kind, int num) {
        super(); // Object 클래스 생성자 호출 (명시적으로 쓸 필요는 없음)
        this.kind = kind;
        this.num = num;
    }

    /**
     * 정적 메서드(static method)
     * 카드의 공통적인 크기 정보를 출력함
     */
    public static void size() {
        System.out.println(WIDTH + "X" + HEIGHT);
    }

    /**
     * 인스턴스 메서드
     * 각 카드 객체의 정보를 출력함
     */
    public void info() {
        System.out.println(kind + ":" + num);
    }

    /**
     * 정적 메서드
     * 제작자 정보를 반환
     */
    public static String copyWriter() {
        return "Kim";
    }

    /**
     * 정적 메서드
     * 입력받은 이름에 "'s card"를 붙여 반환
     */
    public static String whos(String name) {
        return name + "'s card";
    }
}
