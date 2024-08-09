package day04;

public class SmartPhone extends Phone {
	
	public boolean wifi;
	
	public SmartPhone(String model, String color) {
		super(model, color);
		
		
	}
	
	
	
	@Override
	public void bell() {
		// TODO Auto-generated method stub
		System.out.println("링딩동 링딩동 벨소리가 울린다.");
	}



	public void setWifi(boolean wifi) {
		this.wifi = wifi;
		System.out.println("와이파이 상태를 변경했습니다.");
		
	}
	
	public void internet() {
		System.out.println("인터넷에 연결합니다.");
	}
	
}

