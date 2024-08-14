package day07;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import org.json.JSONObject;

public class ChatClient {

    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    String chatName;

    // 서버와 연결을 수립하는 메서드
    public void connect() throws IOException {
        socket = new Socket("localhost", 50001);

        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());

        System.out.println("서버 연결");
    }

    // 메시지를 수신하는 스레드 실행
    public void receive() {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    String json = dis.readUTF();
                    JSONObject root = new JSONObject(json);

                    String clientIp = root.getString("clientIp");
                    String chatName = root.getString("chatName");
                    String message = root.getString("message");
                    System.out.println("<" + chatName + "@" + clientIp + "> " + message);
                }
            } catch (Exception e1) {
                System.out.println("서버 연결 끊김");
                System.exit(0);
            }
        });
        thread.start();
    }

    // 서버로 메시지를 보내는 메서드
    public void send(String json) throws IOException {
        dos.writeUTF(json);
        dos.flush();
    }

    // 서버와의 연결을 종료하는 메서드
    public void unconnect() throws IOException {
        if (dis != null) dis.close();
        if (dos != null) dos.close();
        if (socket != null && !socket.isClosed()) socket.close();
    }

    public static void main(String[] args) {

        try {
            ChatClient chatClient = new ChatClient(); // 오타 수정
            chatClient.connect();

            Scanner scan = new Scanner(System.in);
            System.out.println("대화명 입력:");

            chatClient.chatName = scan.nextLine();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("command", "incoming");
            jsonObject.put("data", chatClient.chatName);

            String json = jsonObject.toString();
            chatClient.send(json);

            chatClient.receive();

            System.out.println("==============================");
            System.out.println("보낼 메시지를 입력하고 enter");
            System.out.println("채팅을 종료하려면 q 입력");
            System.out.println("==============================");

            while (true) {
                String message = scan.nextLine();
                if (message.equalsIgnoreCase("q")) {
                    break;
                } else {
                    jsonObject = new JSONObject();
                    jsonObject.put("command", "message");
                    jsonObject.put("data", message);
                    json = jsonObject.toString();
                    chatClient.send(json);
                }
            }

            scan.close();
            chatClient.unconnect();

        } catch (IOException e) {
            System.out.println("서버 연결 실패: " + e.getMessage());
        }
    }
}
