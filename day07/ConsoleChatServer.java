package day07;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import java.net.ServerSocket;

public class ConsoleChatServer implements Runnable{

	private static ServerSocket serverSocket = null;
	static Socket socket = null;
	
	public ConsoleChatServer() throws Exception {
		
		
		serverSocket = new ServerSocket(50001);
		System.out.println("서버 시작됨");
		socket = serverSocket.accept();
		System.out.println("클라이언트 접속 성공");
		
		
		BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
		
		PrintWriter pout = new PrintWriter(socket.getOutputStream(), true);
		
		Thread tr = new Thread(this);
		tr.start();
		String msg="";
		
		while ((msg = key.readLine()) != null) {
			pout.println(msg);
			
		}
		
		
		
	}
	
	public static void main(String[] args) throws IOException {
		
		try {
			new ConsoleChatServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	
	@Override 
	public void run() {
		
		try {
			InputStream is = socket.getInputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String clientMsg="";
			
			while(true) {
				clientMsg = br.readLine();
				System.out.println("FROM CLINET" + clientMsg);
				
			}}
			catch (Exception e) {
				System.out.println(" 예외 : " + e.getMessage());
			}
		}
	}

