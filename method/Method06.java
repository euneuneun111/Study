package method;

public class Method06 {
    
    // 정수 2개 더하기 메서드
    public static int sum(int x, int y) {
        return (x + y);
    }
    
    // 정수 3개 더하기 메서드 (오버로딩)
    public static int sum(int x, int y, int z) {
        return (x + y + z);
    }
    
    // 실수(double) 2개 더하기 메서드 (오버로딩)
    public static double sum(double x, double y) {
        return (x + y);
    }
    
    public static void main(String args[]) {
        System.out.println("sum(10, 20) 호출 결과 : " + sum(10, 20));
        System.out.println("sum(10, 20, 30) 호출 결과 : " + sum(10, 20, 30));
        System.out.println("sum(10.5, 20.5) 호출 결과 : " + sum(10.5, 20.5));
    }
}
