package day05;

import day03.Car;

class Car {
	public class Tire {}
	static class Engine{}
}


public class CarExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Car myCar = new Car();
		
		Car.Tire tire = myCar.new Tire();
		Car.Engine engine = new Car.Engine();
		
	}

}
