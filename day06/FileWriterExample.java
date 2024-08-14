package day06;


import java.io.*;

public class FileWriterExample {

    static FileWriter fw = null;
    static BufferedInputStream bis = null;

    public static void main(String[] args) {
        try {
            // 키보드로부터 문자 데이터를 읽어와서 콘솔로 출력
            InputStream is = System.in;
            bis = new BufferedInputStream(is);

            // 버퍼를 사용하여 키보드 입력을 처리
            byte[] buffer = new byte[1024];
            int bytesRead = bis.read(buffer);

            // 읽은 데이터를 문자열로 변환
            String message = new String(buffer, 0, bytesRead).trim();

            // 메시지를 콘솔에 출력
            System.out.println("메시지: " + message);

            // 파일에 메시지를 기록
            fw = new FileWriter("file.txt");
            fw.write(message);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("파일 처리 중 오류가 발생했습니다.");
        } finally {
            try {
                if (fw != null) fw.close();
                if (bis != null) bis.close();
            } catch (IOException e) {
                System.out.println("스트림 닫기 중 오류가 발생했습니다.");
            }
        }
    }
}
