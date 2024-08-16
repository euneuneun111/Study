package day08;

import java.io.*;
import java.net.*;
import java.util.*;


public class MultiServer {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients; // ArrayList 사용

    public MultiServer() {
        try {
            serverSocket = new ServerSocket(5000);
            clients = new ArrayList<>(); // ArrayList 초기화
            System.out.println("서버가 시작되었습니다.");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("새로운 클라이언트가 연결되었습니다.");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler); // ArrayList에 클라이언트 추가
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MultiServer();
    }

    class ClientHandler implements Runnable {
        private Socket socket;
        private DataInputStream dis;
        private DataOutputStream dos;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String msg;
                while ((msg = dis.readUTF()) != null) {
                    System.out.println("클라이언트로부터 받은 메시지: " + msg);
                    broadcast(msg);
                }
            } catch (IOException e) {
                System.out.println("클라이언트가 연결을 종료했습니다.");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void broadcast(String msg) {
            for (ClientHandler client : clients) {
                try {
                    client.dos.writeUTF(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
