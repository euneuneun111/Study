package day07;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;

public class FilesExample {

    public static void main(String[] args) {
        try {
            String data = "id: winter, email: winter@minj";

            Path path = Paths.get("C:\\Temp\\user.txt");

            // 파일에 문자열 쓰기
            Files.writeString(path, data, Charset.forName("UTF-8"));

            // 파일 유형과 크기 출력
            String type = Files.probeContentType(path);
            System.out.println("파일 유형: " + type);
            System.out.println("파일 크기: " + Files.size(path) + " bytes");

            // 파일 내용 읽기
            String content = Files.readString(path, Charset.forName("UTF-8"));
            System.out.println("파일 내용: " + content);

        } catch (IOException e) {
            e.printStackTrace();  // 예외 발생 시 스택 트레이스를 출력
        }
    }
}
