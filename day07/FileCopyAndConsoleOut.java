package day07;

// 데이터 소스
// 데이터 목적지
// filereader, writer , outputstream 
// 파일 복사 기능 구현

import java.io.*;

public class FileCopyAndConsoleOut {

    public static void main(String[] args) {
        String fileName = "cpt.txt";

        try {
            FileReader fr = new FileReader("Fileout.java");
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);

            // 콘솔 출력용 스트림
            OutputStreamWriter osw = new OutputStreamWriter(System.out);

            int n;
            while ((n = br.read()) != -1) {
                // 파일에 쓰기
                bw.write(n);
                bw.flush();

                // 콘솔에 출력
                osw.write(n);
                osw.flush();
            }

            // 리소스 닫기
            br.close();
            bw.close();
            osw.close();

        } catch (Exception e) {
            e.printStackTrace();  // 예외 발생 시 스택 트레이스를 출력
        }
    }
}
