package day03;
import java.util.ArrayList;

public class MemberTest {

    public static void main(String[] args) {
        Member user1 = new Member("홍길동", "hong");
        
        ArrayList<Member> memlist = new ArrayList<>();
        
        memlist.add(user1);
        memlist.add(new Member("강길동", "kang"));
        
        for (Member m : memlist) {
            System.out.println(m.getName() + "\t" + m.getId());
        }
        
        MemberService memberService = new MemberService();
        
        boolean result = memberService.login("hong", "12345");
        
        if (result) {
            System.out.println("로그인이 되었습니다.");
            memberService.logout("hong");
        } else {
            System.out.println("id 또는 비밀번호가 일치하지 않습니다.");
        }
    }
}