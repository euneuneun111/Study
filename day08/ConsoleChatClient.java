package day08;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ConsoleChatClient implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ConsoleChatClient() {
        try {
            // 서버에 연결
            socket = new Socket("192.168.0.3", 5000);
            System.out.println("서버와 연결 성공");

            // 서버와의 통신을 위한 입력 및 출력 스트림 생성
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // 서버로부터 메시지를 수신하기 위한 스레드 시작
            Thread receiveThread = new Thread(this);
            receiveThread.start();

            // 사용자 입력을 읽기 위한 스캐너
            Scanner scanner = new Scanner(System.in);
            String msg;
            while (true) {
                // 사용자 입력을 읽고 서버로 전송
                msg = scanner.nextLine();
                out.println(msg);
            }

        } catch (IOException e) {
            System.out.println("연결 오류: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String serverMsg;
            while ((serverMsg = in.readLine()) != null) {
                // 서버로부터 받은 메시지를 출력
                System.out.println("서버로부터: " + serverMsg);
            }
        } catch (IOException e) {
            System.out.println("서버로부터 읽기 오류: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ConsoleChatClient();
    }
}
