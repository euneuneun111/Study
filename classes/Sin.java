package classes;

public class Sin {

    // 인스턴스 변수: 각 객체가 가지는 각도 값
    int angle = 0;

    /**
     * 인스턴스 메서드
     * 객체가 가진 angle 값을 기준으로 Math.sin 계산
     * 사용 시: Sin s = new Sin(); s.angle = 30; s.sin();
     */
    public double sin() {
        return Math.sin(angle);
    }

    /**
     * 정적 메서드
     * 전달받은 각도를 기준으로 Math.sin 계산
     * 사용 시: Sin.sin(30);
     */
    public static double sin(int angle) {
        return Math.sin(angle);
    }

}
