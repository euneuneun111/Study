package day05;

public class Teacher extends Person {

    private String Tid;
    private String cNumber;

    public Teacher(String Tid, String cNumber, String name, String age) {
        super(name, age);
        this.Tid = Tid;
        this.cNumber = cNumber;
    }

    public void setTid(String Tid) {
        this.Tid = Tid;
    }

    public String getTid() {
        return Tid;
    }

    public void setcNumber(String cNumber) {
        this.cNumber = cNumber;
    }

    public String getcNumber() {
        return cNumber;
    }
}
