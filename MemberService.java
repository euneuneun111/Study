package day03;

public class MemberService {

    public boolean login(String id, String password) {
        if ("hong".equals(id) && "12345".equals(password)) {
            System.out.println("로그인이 되셨습니다.");
            return true;
        } else {
            return false;
        }
    }
    
    public void logout(String id) {
        System.out.println(id + "님이 로그아웃 되었습니다.");
    }
}