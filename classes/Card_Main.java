package classes;

public class Card_Main {

	public static void main(String[] args) {
		
		// 클래스(static) 변수 직접 출력 (공통 크기)
		System.out.println(Card.WIDTH);   // 100
		System.out.println(Card.HEIGHT);  // 200

		// 카드 객체 2개 생성
		Card card1 = new Card("Heart", 7);     // 하트 7번 카드
		Card card2 = new Card("Diamond", 8);   // 다이아 8번 카드

		// static 변수(WIDTH)를 다양한 방식으로 변경
		Card.WIDTH = 1000;      // 클래스 이름으로 변경
		card1.WIDTH = 2000;     // 객체를 통해 접근해도 공유됨
		card2.WIDTH = 3000;     // 또 변경 → 마지막 값이 모든 객체에 적용됨

		// 각 카드의 종류 출력
		System.out.println(card1.kind);   // Heart
		System.out.println(card2.kind);   // Diamond

		// 정적 메서드 호출 - 현재 카드 크기 출력
		Card.size();      // 3000X200 (최종적으로 설정된 값 기준)
		card1.size();     // 객체로 호출해도 동일 (비추천 방식)
		card2.size();

		// 인스턴스 메서드 호출 - 개별 카드 정보 출력
		card1.info();     // Heart:7
		card2.info();     // Diamond:8

		// 정적 메서드 - 카드 제작자 이름 출력
		System.out.println(Card.copyWriter()); // Kim

		// 정적 메서드 - 이름 넣으면 "이름's card" 형태로 출력
		System.out.println(Card.whos("Tom"));   // Tom's card
	}
}

