package day07;

import java.io.IOException;

public class INputStreamTest {

   public static void main(String[] args) {
      //데이터 소스 : 키보드
      //데이터 목적지 : 모니터
      //read()
      //InputStream의 read()메소드는 바이트 입력을 리턴하고
      //입력이 종료되면 (Ctrl+C나 Ctrl+D) -1을 출력한다
     
      try {
         int r = 0;
         int count = 0;
         while(true) {
            r=System.in.read();

            count++;

            if(r == 'x'||r=='X') {
               break;
            }
           
         System.out.println("r = "+(char)r);     
         }
         System.out.println("************************");
         System.out.println(count+"bytes 읽음");
         System.out.println("************************");
         System.in.close();
      }catch(IOException e) {
            e.getMessage();
         }
   }
}
