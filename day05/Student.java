package day05;

public class Student extends Person {

    private String Stid;
    private String cName;

    public Student(String Stid, String cName, String name, String age) {
        super(name, age);
        this.Stid = Stid;
        this.cName = cName;
    }

    public void setStid(String Stid) {
        this.Stid = Stid;
    }

    public String getStid() {
        return Stid;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcName() {
        return cName;
    }

    @Override
    public String personInfo() {
        String info = super.personInfo();
        info += "\nID: " + Stid + "\n학급 이름: " + cName;
        return info;
    }
}
