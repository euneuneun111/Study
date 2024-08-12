package day05;
import java.util.ArrayList;

public class SchoolApp {

    public static void main(String[] args) {
        Person person;
        ArrayList<Person> personlist = new ArrayList<Person>();

        person = new Student("20240101", "자바", "홍길동", "20");
        personlist.add(person);

        personlist.add(new Staff("202020", "eoott", "박상앙"));
        personlist.add(new Teacher("212020", "qoott", "박앙", "32"));  // 나이를 32로 수정

        for (Person p : personlist) {
        	if (p instanceof Student) {
            System.out.println(((Student) p). getStid());
        } else if (p instanceof Teacher) {
            System.out.println(((Teacher) p). getTid());
    } else if (p instanceof Staff) {
        System.out.println(((Staff) p). getSid());

    } else 
        System.out.println("null");
}
}
}
