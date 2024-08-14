package day07;

import java.io.*;

public class FileOut {

    public static void main(String[] args) {
        // 사용자로부터 파일에 저장할 내용을 입력받아 파일에 쓰기

        System.out.println("파일로 저장할 내용을 입력하세요");

        // 파일명 설정 (저장될 파일)
        String fileName = "output.txt";

        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            int n = 0;

            // 사용자 입력을 받아 파일에 쓰기
            while ((n = System.in.read()) != -1) {
                fos.write(n);  // 입력받은 데이터를 파일에 씀
                fos.flush();  // 버퍼에 남은 데이터를 강제로 파일에 씀
            }
        } catch (IOException e) {
            // 입출력 예외 처리
            System.out.println("파일로 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
