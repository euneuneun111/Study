package Collection;

import java.util.HashSet;
import java.util.Set;

public class Member_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Member mem1 = new Member("mimi", "mimi","mimi"); // °´Ã¼ »ý¼º 
		Member mem2 = new Member("mimi", "mimi","mimi");
		Member mem3 = new Member("mimi", "mimi","mimi");
		
		

		// List<Member> memberList = new ArrayList<Member>();
		Set<Member> memberList = new HashSet<Member>();
		memberList.add(mem1);
		memberList.add(mem2);
		memberList.add(mem3);

		Member target = new Member("mimi", null, null);
		
		System.out.println(memberList.contains(target));
		// System.out.println(memberList.indexOf(target));
		
		memberList.remove(target);
		
		
		System.out.println(memberList.size());
		System.out.println(memberList);
		

	}


}
