package day06;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.*;

public class DataOutStream {

	static DataOutputStream dos = null;
	static DataInputStream dis = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			dos = new DataOutputStream(System.out);
			dis = new DataInputStream(System.in);

			dos.writeInt(111);
			dos.writeDouble(155.2);
			dos.writeBoolean(true);
			dos.writeChar('가');
			dos.writeUTF("문자열 입력하기");

			System.out.println("정수값: " + dis.readInt());
			System.out.println("실수값: " + dis.readDouble());
			System.out.println("논리값: " + dis.readBoolean());
			System.out.println("문자값: " + dis.readChar());
			System.out.println("문자열값: " + dis.readUTF());

		} catch (IOException e) {
			e.getMessage();
		}

	}

}
