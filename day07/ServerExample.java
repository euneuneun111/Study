package day07;

import java.net.*;
import java.io.*;

public class ServerExample {

	// 서버 소켓

	static ServerSocket ser = null;
	static Socket sock = null;

	static DataInputStream dis = null;
	static DataOutputStream dos = null;

	public static void startServer() throws IOException {

		System.out.println("접속 대기");
		ser = new ServerSocket(2000);

		sock = ser.accept();
		System.out.println("클라이언트 접속 성공");

		dos = new DataOutputStream(sock.getOutputStream());

		String message = "반갑습니다.";

		dos.writeUTF(message);
		dos.flush();

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		startServer();

	}
}
