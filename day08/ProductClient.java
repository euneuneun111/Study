package day08;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ProductClient {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public static void main(String[] args) {
        new ProductClient().startClient();
    }

    public void startClient() {
        try {
            // 서버에 연결
            socket = new Socket("127.0.0.1", 50001); // 서버의 IP 주소와 포트 번호
            System.out.println("서버에 연결 성공");

            // 서버와의 통신을 위한 스트림 생성
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            // 서버로부터 메시지를 수신하기 위한 스레드 시작
            Thread receiveThread = new Thread(new ReceiveMessages());
            receiveThread.start();

            // 사용자 입력을 읽기 위한 스캐너
            Scanner scanner = new Scanner(System.in);
            String msg;
            while (true) {
                // 사용자 입력을 읽고 서버로 전송
                msg = scanner.nextLine();
                dos.writeUTF(msg);
            }

        } catch (IOException e) {
            System.out.println("연결 오류: " + e.getMessage());
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ReceiveMessages implements Runnable {
        @Override
        public void run() {
            try {
                String serverMsg;
                while ((serverMsg = dis.readUTF()) != null) {
                    // 서버로부터 받은 메시지를 출력
                    System.out.println("서버로부터: " + serverMsg);
                }
            } catch (IOException e) {
                System.out.println("서버로부터 읽기 오류: " + e.getMessage());
            }
        }
    }
}
