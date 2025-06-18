package quiz;

package quiz;

public class Gugudan {
    // 구구단 시작 단과 끝 단 (기본값 2~9)
    int startDan = 2, maxDan = 9;
    // 각 단의 곱셈 시작 값과 끝 값 (기본값 1~9)
    int startGop = 1, maxGop = 9;
    // 결과를 저장할 문자열
    String result = "";

    // 기본 생성자 (기본값 사용)
    public Gugudan() {
    }

    // 범위를 지정하는 생성자
    public Gugudan(int startDan, int maxDan, int startGop, int maxGop) {
        this.startDan = startDan;
        this.maxDan = maxDan;
        this.startGop = startGop;
        this.maxGop = maxGop;
    }

    // 구구단 계산 및 문자열로 결과 저장
    public void gugudan() {
        for (int dan = startDan; dan <= maxDan; dan++) {
            result += dan + "단\n";
            for (int gop = startGop; gop <= maxGop; gop++) {
                result += dan + " * " + gop + " = " + (dan * gop) + "\n";
            }
            result += "\n";  // 단마다 빈 줄 추가
        }
    }

    // 저장된 구구단 결과 출력
    public void print() {
        System.out.println(result);
    }
}
