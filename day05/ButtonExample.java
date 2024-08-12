package day05;

import java.awt.*;

class Button {
	// 정적 중첩 인터페이스
	
	public static interface ClickListener {
		void onClick();
	}
 
 
	private ClickListener clistener;
	
	public void setClickListener(ClickListener clistener) {
		this.clistener = clistener;
	}
	
	public void onClick() {
		this.clistener.onClick();
	}
	
}
	
	
public class ButtonExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Button okbtn = new Button();
		
		class OkListener implements Button.ClickListener{

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				System.out.println("ok 버튼을 클릭했습니다.");
				
			}
			
			
			
		}
		

	}

}
