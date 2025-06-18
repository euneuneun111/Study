package quiz;

public class Gugudan_Main {

    public static void main(String[] args) {
        // 3단부터 7단까지, 1부터 13까지 곱하는 구구단 객체 생성
        Gugudan ex = new Gugudan(3, 7, 1, 13);

        // 구구단 계산 수행 (결과를 result 문자열에 저장)
        ex.gugudan();

        // 저장된 구구단 결과 출력
        ex.print();
    }
}
