package day04;

public class Person {
	
	//객체 모델링, 클래스 맴버
	
	//속성
	int age;
	String name;
	char gender;
	String tel;
	
	
	//생성자 
	public Person() {
		
	}
	
	//메소드
	void 웃다() {
		System.out.println("웃는다.");
	}
	
	void 먹다() {
		System.out.println("먹는다.");
	}
	
	void getinfo() {
		System.out.println("정보");
		System.out.println("이름 : " +  name + "\n" + "나이 : " + age + "\n" + "성별 : " + gender + "\n"+ "전화번호 : " + tel + "\n");
	}

}
