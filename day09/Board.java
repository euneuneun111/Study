package day09;

public class Board {
    private int bno;
    private String btitle;
    private String bcontent;

    // 인자 생성자
    public Board(int bno, String btitle, String bcontent) {
        this.bno = bno;
        this.btitle = btitle;
        this.bcontent = bcontent;
    }

    // Getter 메서드
    public int getBno() {
        return bno;
    }

    public String getBtitle() {
        return btitle;
    }

    public String getBcontent() {
        return bcontent;
    }

    // 객체 정보 출력 메서드
    @Override
    public String toString() {
        return "Board [bno=" + bno + ", btitle=" + btitle + ", bcontent=" + bcontent + "]";
    }
}
