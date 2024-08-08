package day03;

public class Car {
	
	//객체 모델링, 클래스 맴버
	
		//속성
		int sang;
		
		String name;
		String com;
		String model;
		String color;
		int MAX_SPEED;
		
		
		//인자 생성자 - 오버로딩
		public Car(String com, String model, String color, int MAX_SPEED) {
			this.com = "하하" ;
			this.model = "bmw" ;
			this.color = "white";
			this.MAX_SPEED = 400;
			
			
		}
		
		public Car(String com, String model, String color) {
			this.com = "하하" ;
			this.model = "benz" ;
			this.color = "white";
			
		}
		
		//메소드
		void 최신형() {
			System.out.println("최신형");
		}
		
		void 국산() {
			System.out.println("구형");
		}

		void 외재() {
			System.out.println("외재");
		}

}
