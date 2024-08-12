package day05;

abstract class Animal { 
	// 추상 클래스 적어도 한개이상의 추상메소드를 갖는 메소드
	// 추상 메소드 바디가 없는 메소드 
	
	abstract void sound();
	
}

class Dog extends Animal{

	@Override
	void sound() {
		// TODO Auto-generated method stub
		System.out.println("야옹 야옹");
		
		
	}
	
	
}



public class AnimalTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Animal animal = new Dog();
		animal.sound();
		
		
		
	}

}
