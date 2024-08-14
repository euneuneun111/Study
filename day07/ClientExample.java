package day07;

import java.net.*;
import java.io.*;

public class ClientExample {

	Socket socket;

	static DataInputStream dis = null;
	static DataOutputStream dos = null;

	public ClientExample() throws IOException {

		socket = new Socket("Localhost", 2000);
		System.out.println("서버와 연결 성공");

		dis = new DataInputStream(socket.getInputStream());

		String mes = dis.readUTF();
		System.out.println("서버 : " + mes);

		socket.close();
		System.out.println(" 서버와 연결 끊김");

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		new ClientExample();

	}

}
