package test;

public class Method_ex {

	public static void main(String[] args) {
		
		System.out.println("main ����");
		
		System.out.println("println() ����");
		System.out.println(sum(1,4));
		System.out.println("println() ����");
		
		
		System.out.println("main ����");

	}
	
	public static int sum(int a, int b) {
		System.out.println("sum() ����");
		System.out.println("sum() ����");
		return a+b;
	}

}