package day05;

interface Vehicle {
	

	public void run();
	
}

class bus implements Vehicle {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println("버스가 달린다.");
	}
	
}

class Taxi implements Vehicle {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println("택시가 달린다.");
	}
	
}

class Driver {
	void drive(Vehicle v) {
		System.out.println("운전자: ");
		v.run();
		
	}
}


public class DriverTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

			Driver driver = new Driver();
			
			Bus bus = new Bus();
			driver.drive(bus);
			driver.drive(new Taxi());
			
			
			
	}

}
