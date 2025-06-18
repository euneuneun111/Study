package classes;

public class Person_Main {

	public static void main(String[] args) {
		// Person 클래스의 객체 2개 생성
		Person p = new Person();
		Person pp = new Person();

		// 첫 번째 객체의 필드 값 설정
		p.name = "kim";
		p.reg_num = 11;

		// 두 번째 객체의 필드 값 설정
		pp.name = "Pak";
		pp.reg_num = 33;

		// 각 객체의 필드 출력
		System.out.println(p.name);       // kim
		System.out.println(p.reg_num);    // 11

		System.out.println(pp.name);      // Pak
		System.out.println(pp.reg_num);   // 33

		// 참조 변수 pp에 p 객체 주소를 대입 → pp가 p를 참조하게 됨
		pp = p;

		// pp는 이제 p와 같은 객체를 가리키므로 같은 값이 출력됨
		System.out.println(pp.name);      // kim
		System.out.println(pp.reg_num);   // 11
	}
}
