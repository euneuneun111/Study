package day04;
import java.util.ArrayList;


public class GameCharacterTest {

	//게임 캐릭터 초기 작업을 위한 초심자 - novice 클래스
	// 초심자 다음 직업인 마법사 - wizard 클래스 
	// wizard 클래스가 novice 클래스의 모든 필드와 메소드를 가지고 있고
	// 추가로 마력 필드와 불 공격 메소드 fireball()이 더해져 있음
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	    
		
		Wizard wizard = new Wizard();
		wizard.name = "간달프";
		wizard.hp = 20;
		wizard.mp = 40;
		
		Knight knight = new Knight();
		knight.name = "전사";
		knight.hp = 40;
		knight.stm = 20;
		
		wizard.punch();
		knight.cutter();
		
		  ArrayList<Novice> gamelist = new ArrayList<>();

		  gamelist.add(wizard);
		  gamelist.add(knight);
		  
		  
		  for (Novice a : gamelist) {
			  if (a instanceof Wizard) {
				  ((Wizard) a). fireball();
			  } else if (a instanceof Knight) {
				  ((Knight) a). cutter();
			  }
			  
		  }
		  
	}

}


class Novice {
	String name;
	int hp;
	
	void punch() {
		System.out.printf("%s(HP : %d) 의 펀치 ! \n", name, hp);
	}
}


class Wizard extends Novice {
	
	int mp;
	
	void fireball() {
		System.out.printf("%s(mp : %d) 의 불 공격 ! \n", name, mp);
	}
	
}

class Knight extends Novice {
	
	int stm;
	
	void cutter() {
		System.out.printf("%s(stm : %d) 의 베기 공격 ! \n", name, stm);
	}
	
}


