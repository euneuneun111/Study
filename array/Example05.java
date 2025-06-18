package array;

public class Example05 {
    public static void main(String[] args) {
        // 문자열 리터럴 (문자열 상수 풀에 저장됨)
        String s1 = "Java";
        String s2 = "Java";

        // new 연산자를 통해 힙 영역에 새로운 객체 생성
        String s3 = new String("Java");
        String s4 = new String("Java");

        // == : 주소(참조값) 비교
        System.out.println(s1 == s2); // true - 같은 리터럴, 같은 참조
        System.out.println(s1 == s3); // false - 다른 객체 (new로 생성됨)
        System.out.println(s3 == s4); // false - 각각 new로 만들어진 서로 다른 객체
        
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
        // .equals() : 문자열 내용(값) 비교
        System.out.println(s1.equals(s4)); // true - 값이 같음
        System.out.println(s1.equals(s4)); // true - 값이 같음 (중복 출력)
        System.out.println(s3.equals(s4)); // true - 값이 같음
    }
}
