package day07;

import java.io.IOException;
import java.net.InetAddress;

public class InetAddressExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try {
			InetAddress local = InetAddress.getLocalHost();
			System.out.println("내 컴퓨터 주소 : " + local);

			InetAddress[] locals = InetAddress.getAllByName("www.naver.com");

			for (InetAddress in: locals) {
				
				System.out.println("naver.com ip주소 : " + in.getHostAddress());
			}
			
		} catch(IOException en ) {
			
		}
		
		
	}

}
