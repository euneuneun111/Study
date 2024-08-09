package day04;

public class Carexample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Car car = new Car();
		
		car.tire = new Tire();
		
		car.tire = new HankookTire();
		car.run();
		
		car.tire = new KumhoTire();
		car.run();
 
		car.setSpeed(100);
		car.setStop(false);
		
		System.out.println(car.getSpeed());
		System.out.println(car.getStop());
	}

}

class Car {
	// 캡슐화 
	
	private int speed;
	private boolean stop;
	
	public Tire tire;
	
	public void run() {
		tire.roll();
	}
	
	
	// setter 메소드 정의 필드 값 변경하는 메소드
	
	public void setSpeed(int speed) {
		this.speed = speed;	
	}
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public boolean getStop() {
		return stop;
	}
	
	
	
}