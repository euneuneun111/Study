package day10;

import java.sql.*;
import java.io.*;

public class ConnectDBTest {

	static Connection con;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ConnectDB cdb = new ConnectDB();

		con = cdb.getCon();

		if (con == null) {
			System.out.println("연결 객체 생성 실패");
		} else {
			System.out.println("연결 객체 생성 성공");

		}
	}
	


}
