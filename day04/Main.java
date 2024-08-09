package day04;


import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        House house1 = new House("은광", 3, "대전");
        
        house1.Showowner(); // ownername을 메소드 내부에서 사용하도록 변경
        System.out.println("방의 개수 : " + house1.getRoomNum());
        house1.Showinfo2();
        
        ArrayList<House> arrList = new ArrayList<>();
        arrList.add(house1);
        arrList.add(new House("김감자", 4, "강원도"));
        arrList.add(new House("김홍시", 4, "인천"));
        
        for (House house : arrList) {
            house.Showinfo2();
        }
    }
}

class House {
    String ownerName;
    int roomNum;
    String address;
    
    // 기본 생성자
    public House() {}
    
    // 매개변수가 있는 생성자
    public House(String ownerName, int roomNum, String address) {
        this.ownerName = ownerName;
        this.roomNum = roomNum;
        this.address = address;
    }
    
    // 집주인 이름을 출력하는 메소드
    void Showowner() {
        System.out.println("집주인 이름: " + ownerName);
    }
    
    // 방의 개수를 반환하는 메소드
    int getRoomNum() {
        return roomNum;
    }
    
    // 모든 멤버 변수의 값을 반환하는 메소드
    String Showinfo() {
        String result = "집 주인: " + ownerName + "\n" + "방 개수: " + roomNum + "\n" + "집 주소: " + address;
        return result;
    }
    
    // 모든 멤버 변수의 값을 출력하는 메소드
    void Showinfo2() {
        System.out.println("집 주인: " + ownerName + "\n" + "방 개수: " + roomNum + "\n" + "집 주소: " + address);
    }
}
