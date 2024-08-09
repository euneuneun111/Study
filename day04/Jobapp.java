package day04;

import java.util.ArrayList;
import java.util.Scanner;

public class Jobapp {

    private ArrayList<Person> persons = new ArrayList<>(); // 구직자 정보를 저장할 ArrayList

    public void showMenu() {
        System.out.println("======================================");
        System.out.println("===========1. 구직 등록===============");
        System.out.println("===========2. 구인 등록===============");
        System.out.println("===========3. 구직자 정보=============");
        System.out.println("===========4. 구인 회사 정보==========");
        System.out.println("===========5.     종료      ==========");
        System.out.println("======================================");
        System.out.println("========메뉴 번호를 입력하세요========");
        System.out.println("======================================");
    }

    public void inputPerson() {
        Scanner sc = new Scanner(System.in);
        Person p1 = new Person();
        System.out.println("구직 등록");

        System.out.println("이름 입력");
        String nm = sc.next();
        p1.name = nm;

        System.out.println("나이 입력");
        int a = sc.nextInt();
        p1.age = a;

        System.out.println("성별 입력");
        System.out.println("성별 메뉴 1. 남자 2. 여자");
        int s = sc.nextInt();
        if (s == 1) {
            p1.gender = 'M';
        } else if (s == 2) {
            p1.gender = 'F';
        } else {
            System.out.println("잘못된 입력입니다. 다시 시도해 주십시오.");
            return; // 잘못된 입력 시 메소드 종료
        }

        System.out.println("전화번호 입력");
        String phone = sc.next();
        p1.tel = phone;

        persons.add(p1); // 구직자 정보를 리스트에 추가
        System.out.println("구직자가 등록되었습니다.");
    }

    public void getinfo() {
        if (persons.isEmpty()) {
            System.out.println("구직자 정보가 없습니다.");
        } else {
            for (Person person : persons) {
                person.getinfo(); // 각 구직자 객체의 getinfo 메소드 호출
            }
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Jobapp ja = new Jobapp();

        while (true) {
            ja.showMenu();
            int no = scan.nextInt();

            if (no == 5) {
                System.out.println("프로그램 종료");
                System.exit(0);
            } else if (no == 1) {
                ja.inputPerson(); // inputPerson 메소드 호출
            } else if (no == 3) {
                ja.getinfo(); // getinfo 메소드 호출
            } else {
                System.out.println("잘못된 입력입니다. 다시 시도해 주십시오.");
            }
        }
    }
}
