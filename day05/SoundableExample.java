package day05;


interface Soundable{
	
	public String sound();

}


class Dog1 implements Soundable {

	@Override
	public String sound() {
		// TODO Auto-generated method stub

		return "멍멍" ;
		
	}
}

class Cat implements Soundable {
	
	@Override
	public String sound() {
		// TODO Auto-generated method stub
	
		return "야옹";
		
	}
}

public class SoundableExample {

	
	public static void printSound(Soundable soundable) {
		System.out.println(soundable.sound());
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		printSound(new Dog1());
		printSound(new Cat());


	}
	
	

}
