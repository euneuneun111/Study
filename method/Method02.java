package method;

public class Method02 {
    
    // 매개변수 없이 두 수를 나누고 결과를 반환하는 메서드
    public static int div() {
        int a = 10;
        int b = 5;
        int result = a / b;  // 10 나누기 5
        
        return result;       // 결과 반환 (2)
    }
    
    public static void main(String[] args) {
        int num = div();     // div 메서드 호출 후 결과를 num에 저장
        System.out.println(num);  // 결과 출력: 2
    }
}
