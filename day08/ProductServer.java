package day08;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductServer {

    private ServerSocket serverSocket;
    private ExecutorService threadPool;
    private List<Product> products;

    public static void main(String[] args) {
        new ProductServer().startServer();
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(50001);
            threadPool = Executors.newFixedThreadPool(100); // 스레드 풀 생성
            products = new Vector<>(); // Vector는 스레드에 안전한 List 구현체

            System.out.println("서버 시작됨");

            while (true) {
                Socket socket = serverSocket.accept(); // 클라이언트 연결 대기
                SocketClient sc = new SocketClient(socket);
                threadPool.execute(sc); // 클라이언트 처리를 위해 스레드 풀에 제출
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stopServer(); // 서버 종료 시 리소스 해제
        }
    }

    class SocketClient implements Runnable { // Runnable 인터페이스 구현
        Socket socket;
        DataInputStream dis;
        DataOutputStream dos;

        public SocketClient(Socket socket) {
            try {
                this.socket = socket;
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String request = dis.readUTF(); // 클라이언트 요청 수신
                    // 클라이언트의 요청을 처리합니다. 예를 들어:
                    // "ADD_PRODUCT", "GET_PRODUCT_LIST" 등의 명령어를 해석할 수 있습니다.
                    dos.writeUTF("서버로부터의 응답: " + request);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close(); // 소켓 종료
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            if (threadPool != null && !threadPool.isShutdown()) {
                threadPool.shutdown(); // 스레드 풀 종료
            }
            System.out.println("서버 종료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
