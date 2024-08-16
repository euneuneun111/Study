package day08;

import java.util.*;

//Board 클래스 정의
class Board {
 private String title;
 private String content;

 public Board(String title, String content) {
     this.title = title;
     this.content = content;
 }

 public String getTitle() {
     return title;
 }

 public String getContent() {
     return content;
 }
}

//BoardDao 클래스 정의
class BoardDao {
 // Board 객체를 담은 리스트를 반환하는 메서드
 public List<Board> getBoardList() {
     List<Board> list = new ArrayList<>();
     list.add(new Board("Title1", "Content1"));
     list.add(new Board("Title2", "Content2"));
     list.add(new Board("Title3", "Content3"));
     list.add(new Board("Title4", "Content4"));
     list.add(new Board("Title5", "Content5"));
     return list;
 }
}

//ListExample 클래스 정의
public class ListExample {
 public static void main(String[] args) {
     // BoardDao 객체 생성
     BoardDao dao = new BoardDao();
     // Board 리스트 가져오기
     List<Board> list = dao.getBoardList();
     // 리스트의 모든 Board 객체 출력
     for(Board board : list) {
         System.out.println(board.getTitle() + " - " + board.getContent());
     }
 }
}
