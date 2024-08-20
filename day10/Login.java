package day10;

import java.util.Scanner;
import java.sql.*;

public class Login {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scan = new Scanner(System.in);

		System.out.println("로그인 화면");
		System.out.print("아이디 : ");
		String uid = scan.nextLine();
		
		System.out.print("비밀번호 : ");
		String upwd = scan.nextLine();

		Users user = new Users(uid, upwd);
		
		
	     // DataModel 객체 생성 후 Users 객체 전달
        DataModel dataModel = new DataModel(user);
        
        // 예시: 로그인 성공 메시지 출력
        System.out.println("로그인 성공! 사용자 아이디: " + user.getid());
        
   
        
	}

}
