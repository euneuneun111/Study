package day05;

public class Staff extends Person {

    private String Sid;

    public Staff(String Sid, String name, String age) {
        super(name, age);
        this.Sid = Sid;
    }

    public void setId(String Sid) {
        this.Sid = Sid;
    }

    public String getSid() {
        return Sid;
    }
}
