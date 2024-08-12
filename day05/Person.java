package day05;
import java.util.Scanner;


public class Person {

    private String name;
    private String age;

    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public String personInfo() {
        return "이름: " + name + "\n나이: " + age;
    }

    public void printAll() {
        System.out.println(this.personInfo());
    }
}