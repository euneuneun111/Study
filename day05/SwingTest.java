package day05;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class SwingTest extends JFrame {
    
    JButton okBtn;
    
    public SwingTest() {
        super("첫번째 스윙연습");
        
        // 버튼 초기화
        okBtn = new JButton("OK");
        
        
        MyListener my = new MyListener();
        
        okBtn.addActionListener(my);
        
        // 버튼을 프레임에 추가
        add(okBtn);
        
        // 기본 닫기 동작 설정
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // 프레임 크기 설정
        setSize(500, 300);
        
        // 프레임을 화면에 보이도록 설정
        show();
    }
    
    //내부 클래스 inner 클래스로 이벤트 처리기 구현
    
    class MyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String result = e.getActionCommand();
			
			if(result.equals("OK")) {
				System.out.println("OK 버튼이 클릭됨");
			}
			
			
			
			
		}
    	
    }
    
    
    public static void main(String[] args) {
        new SwingTest();
    }
}
